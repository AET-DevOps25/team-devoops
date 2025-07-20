from fastapi.testclient import TestClient
from uuid import uuid4
import json
import pytest
from unittest.mock import patch

from service.main import app

client = TestClient(app)

def valid_user(overrides=None):
    base = {
        "userID": str(uuid4()),
        "email": "alice@example.com",
        "firstname": "Alice",
        "lastname": "Wunder",
        "birthday": "2000-01-01",
        "gender": "female",
        "degree": "bsc_physics",
        "degreeStart": 2019,
        "interests": [{"name": "quantum mechanics"}, {"name": "astrophysics"}],
        "bio": "Curious explorer of nature and equations."
    }
    if overrides:
        base.update(overrides)
    return base


def test_get_conversation_starters():
    payload = {
        "users": [
            valid_user(),
            valid_user({"firstname": "Bob", "email": "bob@example.com"}),
            valid_user({"firstname": "Carol", "interests": []}),  # No interests
        ]
    }

    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )

    assert response.status_code == 200
    data = response.json()
    assert "conversationsStarters" in data
    assert isinstance(data["conversationsStarters"], list)
    for starter in data["conversationsStarters"]:
        assert "prompt" in starter
        assert isinstance(starter["prompt"], str)


def test_empty_user_list_returns_400():
    payload = {"users": []}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 400
    assert "User list is empty" in response.text


def test_missing_users_key_returns_422():
    payload = {}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )
    # Pydantic validation error for missing required field "users"
    assert response.status_code == 422
    assert "users" in response.text


def test_user_missing_required_field_returns_422():
    user = valid_user()
    del user["email"]  # Required field missing
    payload = {"users": [user]}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 422
    assert "email" in response.text


def test_invalid_uuid_returns_422():
    user = valid_user({"userID": "not-a-uuid"})
    payload = {"users": [user]}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 422
    assert "userID" in response.text


def test_invalid_date_format_returns_422():
    user = valid_user({"birthday": "01.01.2000"})  # Wrong format
    payload = {"users": [user]}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 422
    assert "birthday" in response.text


def test_interests_as_non_list_returns_422():
    user = valid_user({"interests": "not-a-list"})
    payload = {"users": [user]}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 422
    assert "interests" in response.text


def test_no_content_type_header_returns_200():
    payload = {"users": [valid_user()]}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        # No Content-Type header
    )
    # FastAPI is resilient
    assert response.status_code == 200


def test_empty_payload_returns_422():
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data="",  # Empty body
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 422


def test_invalid_json_returns_422():
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data="{bad json}",
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 422


def test_missing_openai_key_raises_500():
    # Simulate missing OPENAI_API_KEY in settings
    class DummySettings:
        openai_api_key = ""

    # Override FastAPI dependency
    app.dependency_overrides[get_settings] = lambda: DummySettings()

    payload = {"users": [valid_user()]}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )

    assert response.status_code == 500
    assert "OPENAI_API_KEY" in response.text

    # Clean up
    app.dependency_overrides = {}

@patch("langchain_community.chat_models.ChatOpenAI.agenerate")
def test_llm_response_processing(mock_agenerate):
    # Mock LLM response structure with multiple starters
    class DummyMessage:
        def __init__(self, content):
            self.content = content

    class DummyGeneration:
        def __init__(self, message):
            self.message = message

    class DummyResponse:
        def __init__(self, generations):
            self.generations = generations

    mock_response = DummyResponse(
        [[DummyGeneration(DummyMessage("- What is your favorite hobby?\n- How did you choose your degree?"))]]
    )
    mock_agenerate.return_value = mock_response

    payload = {"users": [valid_user()]}
    response = client.request(
        method="GET",
        url="/api/v2/genai/conversation-starter",
        data=json.dumps(payload),
        headers={"Content-Type": "application/json"},
    )
    assert response.status_code == 200
    data = response.json()
    starters = data.get("conversationsStarters")
    assert starters is not None
    prompts = [c["prompt"] for c in starters]
    assert "What is your favorite hobby?" in prompts
    assert "How did you choose your degree?" in prompts


def test_root_endpoint():
    response = client.get("/")
    assert response.status_code == 200
    data = response.json()
    assert data.get("message").startswith("Hello World")


def test_metrics_endpoint():
    response = client.get("/metrics")
    assert response.status_code == 200
    assert "text/plain" in response.headers["content-type"]
    assert "genai_requests_total" in response.text
