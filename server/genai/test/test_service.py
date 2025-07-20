from fastapi.testclient import TestClient
from uuid import uuid4
import json

from service.main import app

client = TestClient(app)

def test_get_conversation_starters():
    # Prepare test data with diverse user fields
    payload = {
        "users": [
            {
                "userID": str(uuid4()),
                "email": "alice@example.com",
                "firstname": "Alice",
                "lastname": "Wunder",
                "birthday": "2000-01-01",
                "gender": "female",
                "degree": "bsc_physics",
                "degreeStart": 2019,
                "interests": [
                    {"name": "quantum mechanics"},
                    {"name": "hiking"}
                ],
                "bio": "Curious explorer of nature and equations."
            },
            {
                "userID": str(uuid4()),
                "email": "bob@example.com",
                "firstname": "Bob",
                "lastname": "Builder",
                "birthday": "1999-05-15",
                "gender": "male",
                "degree": "msc_civil_engineering",
                "degreeStart": 2022,
                "interests": [
                    {"name": "construction"},
                    {"name": "board games"},
                    {"name": "coffee"}
                ],
                "bio": "Always building — whether it's bridges or friendships."
            },
            {
                "userID": str(uuid4()),
                "email": "carol@example.com",
                "firstname": "Carol",
                "lastname": "Neumann",
                "birthday": "1997-11-23",
                "gender": "non-binary",
                "degree": "msc_ai",
                "degreeStart": 2021,
                "interests": [],  # No interests listed
                "bio": "Tech enthusiast who enjoys quiet mornings and loud ideas."
            }
        ]
    }

    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )
    print(f"Response status code: {response.status_code}")
    print(f"Response content: {response.content.decode()}")

    assert response.status_code == 200
    data = response.json()
    assert "conversationsStarters" in data
    assert isinstance(data["conversationsStarters"], list)
    assert len(data["conversationsStarters"]) == 3
    for starter in data["conversationsStarters"]:
        assert "prompt" in starter
        assert isinstance(starter["prompt"], str)
