from fastapi import FastAPI, Depends, HTTPException, status, Request, Body
from fastapi.responses import PlainTextResponse
from prometheus_client import Counter, generate_latest, CONTENT_TYPE_LATEST
from service.schemas import UserCollection, ConversationStarterCollection
from service.config import Settings, get_settings
from service.llm_client import AbstractLLMClient, OpenAIClient
from service.prompt_builder import PromptBuilder
from typing import Callable


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


@app.get("/")
async def root():
    # Increment request count for root endpoint
    REQUEST_COUNT.labels(endpoint="/").inc()
    return {"message": "Hello World! Welcome to the GenAI Service!"}


@app.get("/metrics")
async def metrics():
    # Increment request count for metrics endpoint
    REQUEST_COUNT.labels(endpoint="metrics").inc()
    return PlainTextResponse(generate_latest(), media_type=CONTENT_TYPE_LATEST)


# Factory dependency to provide LLM client instance
def get_llm_client(
    settings: Settings = Depends(get_settings),
) -> AbstractLLMClient:
    # Modularity: add logic to switch clients here (if needed)
    return OpenAIClient(settings.openai_api_key)


@app.post(
    "/api/v2/genai/conversation-starter",
    response_model=ConversationStarterCollection,
    tags=["GenAI"],
    operation_id="post-api-v2-genai-conversation-starter",
    summary="Request conversation starter",
)
async def get_conversation_starter(
    request: Request,
    users: UserCollection = Body(...),
    llm_client: AbstractLLMClient = Depends(get_llm_client),
):
    # Increment request count for conversation starter endpoint
    REQUEST_COUNT.labels(endpoint=str(request.url.path)).inc()

    # Build the prompt using received user data
    try:
        prompt = PromptBuilder.build_conversation_starter_prompt(users.users)
    except ValueError as ve:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail=str(ve))

    # Generate conversation starters using the LLM client
    try:
        content = await llm_client.generate([prompt])
    except Exception as e:
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail=f"LLM error: {e}")

    # Process the LLM response
    starters = [
        line.lstrip("- •*0123456789. ").strip()
        for line in content.split("\n")
        if line.strip()
    ]

    conversation_starters = [{"prompt": starter} for starter in starters]
    return ConversationStarterCollection(conversationsStarters=conversation_starters)
