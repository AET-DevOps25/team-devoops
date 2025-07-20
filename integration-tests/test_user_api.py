"""
User‑service happy‑ & error‑flows – routed through the Gateway.
Two to three scenarios for every endpoint in the OpenAPI spec.
"""
import uuid
import pytest
import requests


# --------------------------------------------------------------------------- #
#  /user/register                                                             #
# --------------------------------------------------------------------------- #

def test_register_happy_flow(base_url, session, new_user_payload):
    r = session.post(f"{base_url}/api/v2/user/register", json=new_user_payload)
    assert r.status_code == 201
    body = r.json()
    assert body["firstName"] == new_user_payload["firstName"]
    assert "userId" in body


def test_register_duplicate_conflict(base_url, session, new_user_payload):
    # 1st – OK
    r1 = session.post(f"{base_url}/api/v2/user/register", json=new_user_payload)
    assert r1.status_code == 201

    # 2nd – expected 409 / 400 (depends on service impl.)
    r2 = session.post(f"{base_url}/api/v2/user/register", json=new_user_payload)
    assert r2.status_code in (400, 409)


# --------------------------------------------------------------------------- #
#  /user/{id}   GET | PUT | DELETE                                            #
# --------------------------------------------------------------------------- #

def test_user_lifecycle(base_url, session, create_user):
    user = create_user()
    uid  = user["userId"]

    # GET existing ----------------------------------------------------------- #
    r_get = session.get(f"{base_url}/api/v2/user/{uid}")
    assert r_get.status_code == 200
    assert r_get.json()["userId"] == uid

    # PUT update ------------------------------------------------------------- #
    patch = {"firstName": "Anastasiia"}
    r_put = session.put(f"{base_url}/api/v2/user/{uid}", json=patch)
    assert r_put.status_code == 200
    assert r_put.json()["firstName"] == "Anastasiia"

    # DELETE ----------------------------------------------------------------- #
    r_del = session.delete(f"{base_url}/api/v2/user/{uid}")
    assert r_del.status_code in (200, 204)

    # GET now 404 ------------------------------------------------------------ #
    r_404 = session.get(f"{base_url}/api/v2/user/{uid}")
    assert r_404.status_code == 404


def test_get_nonexistent_returns_404(base_url, session):
    bogus = uuid.uuid4()
    r = session.get(f"{base_url}/api/v2/user/{bogus}")
    assert r.status_code == 404
