
import requests

BASE_URL = "http://localhost:8080/api/v2/matching"
HEADERS = {
    "Authorization": "Bearer testtoken",
    "Content-Type": "application/json"
}

def auth_headers(valid=True):
    return HEADERS if valid else {"Authorization": "Bearer invalid"}
