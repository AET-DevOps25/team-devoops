"""
Matching‑service scenarios – all via Gateway.
"""
from datetime import datetime, timedelta
import uuid
import requests


def _now_plus(minutes: int):
    return (datetime.utcnow() + timedelta(minutes=minutes)).isoformat(timespec="seconds")


def _match_request_payload(user_id):
    return {
        "userId":        user_id,
        "preferredTime": _now_plus(10),
    }


# --------------------------------------------------------------------------- #
#  /matching/request/submit   + flows                                         #
# --------------------------------------------------------------------------- #

def test_submit_and_match_accept_flow(base_url, session):
    # --- register two users first ------------------------------------------ #
    u1 = session.post(f"{base_url}/api/v2/user/register",
                      json={"firstName": "U1", "lastName": "X", "authId": str(uuid.uuid4())}).json()
    u2 = session.post(f"{base_url}/api/v2/user/register",
                      json={"firstName": "U2", "lastName": "Y", "authId": str(uuid.uuid4())}).json()

    # --- submit two match requests ----------------------------------------- #
    r1 = session.post(f"{base_url}/api/v2/matching/request/submit",
                      json=_match_request_payload(u1["userId"]))
    r2 = session.post(f"{base_url}/api/v2/matching/request/submit",
                      json=_match_request_payload(u2["userId"]))
    assert r1.status_code == r2.status_code == 201

    # --- both users should now appear in /matches/{userId} ----------------- #
    m_resp = session.get(f"{base_url}/api/v2/matching/matches/{u1['userId']}")
    assert m_resp.status_code == 200
    match_list = m_resp.json()["matches"]
    assert match_list                               # at least one match
    match_id = match_list[0]["matchId"]

    # --- happy accept ------------------------------------------------------- #
    acc = session.post(f"{base_url}/api/v2/matching/rsvp/{match_id}/accept")
    assert acc.status_code == 200

    # accepting again ⇒ conflict                                              #
    acc2 = session.post(f"{base_url}/api/v2/matching/rsvp/{match_id}/accept")
    assert acc2.status_code in (400, 409)


def test_reject_then_match_not_found(base_url, session):
    # single user submits → no counterpart yet
    u = session.post(f"{base_url}/api/v2/user/register",
                     json={"firstName": "Solo", "lastName": "Z", "authId": str(uuid.uuid4())}).json()
    r = session.post(f"{base_url}/api/v2/matching/request/submit",
                     json=_match_request_payload(u["userId"]))
    assert r.status_code == 201

    # list matches is empty                                                   #
    m = session.get(f"{base_url}/api/v2/matching/matches/{u['userId']}")
    assert m.status_code == 200
    assert m.json()["matches"] == []

    # bogus rsvp id ---------------------------------------------------------- #
    bogus = uuid.uuid4()
    resp = session.post(f"{base_url}/api/v2/matching/rsvp/{bogus}/reject")
    assert resp.status_code == 404
