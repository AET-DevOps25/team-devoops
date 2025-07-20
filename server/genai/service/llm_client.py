from fastapi import HTTPException, status
from abc import ABC, abstractmethod
from typing import List
from langchain_community.chat_models import ChatOpenAI
from langchain.schema import HumanMessage


class AbstractLLMClient(ABC):
    @abstractmethod
    async def generate(self, prompts: List[str]) -> str:
        """Generate text from prompts asynchronously."""
        pass


class OpenAIClient(AbstractLLMClient):
    def __init__(self, openai_api_key: str):
        if not openai_api_key:
            raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="OPENAI_API_KEY environment variable is missing or empty!")
        self.client = ChatOpenAI(openai_api_key=openai_api_key)

    async def generate(self, prompts: List[str]) -> str:
        messages = [[HumanMessage(content=prompt)] for prompt in prompts]
        response = await self.client.agenerate(messages)
        return response.generations[0][0].message.content
