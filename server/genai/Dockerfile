# ---- Build stage (optional, for dependencies precompilation) ----
FROM python:3.11-slim AS builder

WORKDIR /app
COPY requirements.txt .

# System deps for common LangChain LLM backends (OpenAI, local models, etc.)
RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential curl && \
    pip install --upgrade pip && \
    pip install --prefix=/install -r requirements.txt && \
    apt-get purge -y --auto-remove build-essential && \
    rm -rf /root/.cache /var/lib/apt/lists/*

# ---- Final stage ----
FROM python:3.11-slim

# Create non-root user
RUN useradd -m appuser
WORKDIR /app

# Copy installed Python packages
COPY --from=builder /install /usr/local
COPY service/ ./service
COPY requirements.txt .

# Set env vars (can be overridden in Helm values)
ENV PYTHONUNBUFFERED=1 \
    PORT=80

USER appuser
EXPOSE 80

CMD ["uvicorn", "service.main:app", "--host", "0.0.0.0", "--port", "80"]
