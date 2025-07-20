
import pytest
import requests
from .conftest import auth_headers

def test_update_preferences_valid():
    user_id = "123e4567-e89b-12d3-a456-426614174000"
    payload = {
        "preferences": {
            "location": "Mensa Garching",
            "foodType": "Vegetarian"
        }
    }
    response = requests.put(f"http://localhost:8080/api/v2/user/{user_id}/preferences", json=payload, headers=auth_headers())
    assert response.status_code == 200

def test_update_preferences_unauthorized():
    user_id = "123e4567-e89b-12d3-a456-426614174000"
    payload = {
        "preferences": {
            "location": "Mensa Garching"
        }
    }
    response = requests.put(f"http://localhost:8080/api/v2/user/{user_id}/preferences", json=payload, headers=auth_headers(valid=False))
    assert response.status_code == 401

def test_update_preferences_invalid_data():
    user_id = "123e4567-e89b-12d3-a456-426614174000"
    payload = {
        "preferences": {
            "location": 123  # Should be a string
        }
    }
    response = requests.put(f"http://localhost:8080/api/v2/user/{user_id}/preferences", json=payload, headers=auth_headers())
    assert response.status_code == 400
