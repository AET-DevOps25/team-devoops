from typing import List
from service.schemas import User

class PromptBuilder:
    @staticmethod
    def build_conversation_starter_prompt(users: List[User]) -> str:
        if not users:
            raise ValueError("User list is empty")

        user_descriptions = []
        for user in users:
            interests_str = ", ".join(user.interests) if user.interests else "no interests listed"
            desc = (
                f"{user.firstname} {user.lastname} "
                f"({user.gender}, born {user.birthday.isoformat()}), "
                f"email: {user.email}, "
                f"degree program: {user.degree} (started {user.degreeStart}), "
                f"bio: \"{user.bio}\", "
                f"interests: {interests_str}."
            )
            user_descriptions.append(desc)

        prompt = (
            "You are an assistant generating friendly, inclusive ice-breaker questions "
            "for a group lunch among students. Here are the participants:\n\n"
            + "\n".join(user_descriptions)
            + "\n\nGenerate some short, open-ended conversation-starter questions "
            "that reference their backgrounds and interests."
        )
        return prompt
