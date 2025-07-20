
import pytest
import requests
from .conftest import BASE_URL, auth_headers

def test_demo_match_valid():
    payload = {
        "userId": "123e4567-e89b-12d3-a456-426614174000",
        "date": "2025-07-21",
        "timeSlot": "12:00"
    }
    response = requests.post(f"{BASE_URL}/demo", json=payload, headers=auth_headers())
    assert response.status_code == 200

def test_demo_match_conflict():
    payload = {
        "userId": "conflict-user-id",
        "date": "2025-07-21",
        "timeSlot": "12:00"
    }
    response = requests.post(f"{BASE_URL}/demo", json=payload, headers=auth_headers())
    assert response.status_code == 409
