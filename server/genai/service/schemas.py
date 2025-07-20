from __future__ import annotations

from datetime import date
from typing import List
from uuid import UUID

from pydantic import BaseModel, Field


# ── Core domain models  ───────────────────────────────────────────────────────
class User(BaseModel):
    # Primary key
    userID: UUID = Field(..., description="Unique identifier of the user")

    # Personal data
    email: str
    firstname: str
    lastname: str
    birthday: date
    gender: str = Field("other", description="Gender of the user")

    # Academic data
    degree: str
    degreeStart: int

    # Profile
    interests: List[str]
    bio: str

    class Config:
        json_schema_extra = {
            "example": {
                "userID": "123e4567-e89b-12d3-a456-426614174000",
                "email": "max@example.com",
                "firstname": "Max",
                "lastname": "Mustermann",
                "birthday": "1998-06-15",
                "gender": "male",
                "degree": "msc_informatics",
                "degreeStart": 2024,
                "interests": ["AI", "DevOps"],
                "bio": "Coffee‑powered coder who loves climbing.",
            }
        }


class UserCollection(BaseModel):
    users: List[User]


# ── Conversation starter output ───────────────────────────────────────────────
class ConversationStarter(BaseModel):
    prompt: str


class ConversationStarterCollection(BaseModel):
    conversationsStarters: List[ConversationStarter] = Field(
        ..., alias="conversationsStarters"
    )

    class Config:
        populate_by_name = True
