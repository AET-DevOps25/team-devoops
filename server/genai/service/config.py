from pydantic_settings import BaseSettings
from pydantic import Field
from pathlib import Path

class Settings(BaseSettings):
    openai_api_key: str = Field(..., env="OPENAI_API_KEY")

    class Config:
        env_file = ".env" if Path(".env").exists() else None
        env_file_encoding = "utf-8"

settings = Settings()

def get_settings():
    return settings
