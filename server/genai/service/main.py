from fastapi import FastAPI, Depends, HTTPException, status, Request, Body
from fastapi.responses import PlainTextResponse
from pydantic import ValidationError
from prometheus_client import Counter, generate_latest, CONTENT_TYPE_LATEST
from service.schemas import UserCollection, ConversationStarterCollection
from service.config import Settings, get_settings
from langchain_community.chat_models import ChatOpenAI
from langchain.schema import HumanMessage


app = FastAPI(
    title="GenAI Conversation Starter Service",
    version="2.0",
    description="Suggests conversation starters based on students' interests",
)

REQUEST_COUNT = Counter(
    "genai_requests_total",
    "Total HTTP requests to GenAI service",
    ["endpoint"],
)

@app.get("/metrics")
async def metrics():
    REQUEST_COUNT.labels(endpoint="metrics").inc()
    return PlainTextResponse(generate_latest(), media_type=CONTENT_TYPE_LATEST)


@app.get(
    "/api/v2/genai/conversation-starter",
    response_model=ConversationStarterCollection,
    tags=["GenAI"],
    operation_id="get-api-v2-genai-conversation-starter",
    summary="Request conversation starter",
)
async def get_conversation_starter(
    request: Request,
    users: UserCollection = Body(...),  # FastAPI supports body with GET but it's nonstandard
    settings: Settings = Depends(get_settings),
):
    REQUEST_COUNT.labels(endpoint=str(request.url.path)).inc()

    try:
        if not users.users:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="User list is empty",
            )
        
        # Validate OpenAI API key
        if not settings.openai_api_key:
            raise RuntimeError("OPENAI_API_KEY environment variable is missing or empty!")

        # Build detailed prompt about users and their interests
        # Build rich user descriptions for the LLM prompt
        user_descriptions: list[str] = []
        for user in users.users:
            interests_str = (
                ", ".join(interest.name for interest in user.interests)
                if user.interests
                else "no interests listed"
            )
            desc = (
                f"{user.firstname} {user.lastname} "
                f"({user.gender}, born {user.birthday.isoformat()}), "
                f"email: {user.email}, "
                f"degree program: {user.degree} (started {user.degreeStart}), "
                f"bio: \"{user.bio}\", "
                f"interests: {interests_str}."
            )
            user_descriptions.append(desc)

        prompt = (
            "You are an assistant generating friendly, inclusive ice‑breaker questions "
            "for a group lunch among students. Here are the participants:\n\n"
            + "\n".join(user_descriptions)
            + "\n\nGenerate some short, open‑ended conversation‑starter questions "
            "that reference their backgrounds and interests."
        )
        print(f"Generated prompt:\n{prompt}")  # Debugging output

        # Create a messages list for LangChain ChatOpenAI
        llm = ChatOpenAI(openai_api_key=settings.openai_api_key)
        messages = [[HumanMessage(content=prompt)]]
        response = await llm.agenerate(messages)

        print("Response from LLM:")   # Debugging output
        print(type(response))
        print(response)

        # Extract the AI message content from the nested structure
        content = response.generations[0][0].message.content

        # Clean and extract individual starters from the content
        starters = [
            line.lstrip("- •*0123456789. ").strip()
            for line in content.split("\n")
            if line.strip()
        ]

        # Convert to schema format
        conversation_starters = [{"prompt": starter} for starter in starters]

        # Return in FastAPI-compatible response model
        return ConversationStarterCollection(conversationsStarters=conversation_starters)

    except ValidationError as ve:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail=str(ve))
    except Exception as e:
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=str(e))


@app.get("/")
async def root():
    REQUEST_COUNT.labels(endpoint="/").inc()
    return {"message": "Hello World! Welcome to the GenAI Service!"}
