import os
import uuid
import pytest
import requests

@pytest.fixture(scope="session")
def base_url() -> str:
    """
    Gateway base‑URL. Override in CI with
        GATEWAY_BASE_URL=http://gateway:8080
    """
    return os.getenv("GATEWAY_BASE_URL", "http://localhost:8080").rstrip("/")

@pytest.fixture
def session():
    """One `requests.Session` per test – keeps cookies / connection pool."""
    with requests.Session() as s:
        yield s

# ---------- helper factories -------------------------------------------------

def _uuid():
    return str(uuid.uuid4())

@pytest.fixture
def new_user_payload():
    return {
        "firstName": "Anna",
        "lastName":  "Testova",
        "authId":    _uuid(),
    }

@pytest.fixture
def create_user(base_url, session, new_user_payload):
    """Returns a callable that POSTs /user/register and yields the API object."""
    def _():
        resp = session.post(f"{base_url}/api/v2/user/register", json=new_user_payload)
        assert resp.status_code == 201, resp.text
        return resp.json()
    return _
