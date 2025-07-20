
import pytest
import requests
from .conftest import BASE_URL, auth_headers

def test_submit_valid_request():
    payload = {
        "userId": "123e4567-e89b-12d3-a456-426614174000",
        "date": "2025-07-21",
        "timeSlot": "12:00",
        "preferences": {"location": "Mensa Arcisstraße"}
    }
    response = requests.post(f"{BASE_URL}/request/submit", json=payload, headers=auth_headers())
    assert response.status_code == 200

def test_submit_missing_field():
    payload = {
        "userId": "123e4567-e89b-12d3-a456-426614174000",
        "timeSlot": "12:00"
    }
    response = requests.post(f"{BASE_URL}/request/submit", json=payload, headers=auth_headers())
    assert response.status_code == 400

def test_submit_unauthorized():
    payload = {
        "userId": "123e4567-e89b-12d3-a456-426614174000",
        "date": "2025-07-21",
        "timeSlot": "12:00"
    }
    response = requests.post(f"{BASE_URL}/request/submit", json=payload, headers=auth_headers(valid=False))
    assert response.status_code == 401
