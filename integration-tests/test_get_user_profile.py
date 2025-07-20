
import pytest
import requests
from .conftest import BASE_URL, auth_headers

def test_get_valid_user():
    user_id = "123e4567-e89b-12d3-a456-426614174000"
    response = requests.get(f"http://localhost:8080/api/v2/user/{user_id}", headers=auth_headers())
    assert response.status_code == 200

def test_get_user_not_found():
    user_id = "00000000-0000-0000-0000-000000000000"
    response = requests.get(f"http://localhost:8080/api/v2/user/{user_id}", headers=auth_headers())
    assert response.status_code == 404

def test_get_user_unauthorized():
    user_id = "123e4567-e89b-12d3-a456-426614174000"
    response = requests.get(f"http://localhost:8080/api/v2/user/{user_id}", headers=auth_headers(valid=False))
    assert response.status_code == 401
