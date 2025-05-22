# ------------ Build Stage ------------
    FROM python:3.10-slim AS build
    WORKDIR /app
    
    # Copy necessary files
    COPY pyproject.toml ./
    COPY src ./src
    
    # install build tools
    RUN pip install build

    # build python wheel to be installed later
    RUN python3 -m build
    
    
    # ------------ Runtime Stage ------------
    FROM python:3.10-slim
    WORKDIR /app

    # copy wheel from build
    COPY --from=build /app/dist/*.whl .

    # Install python wheel
    RUN pip install *.whl

    # Expose default HTTP port
    EXPOSE 80

    # Run the conversation_starter.main:start_genai() via the project script
    CMD ["meetatmensa-start-genai"]