# 🍲 Meet@Mensa CI / CD Pipeline

## Step 1 - API Spec:

Meet@Mensa uses openapi-codegen-cli to generate interfaces for clients and servers to ensure contract fidelity. Therefore, the first step of the CI/CD pipeline is to check whether there have been any changes to the API spec. To avoid forcing regeneration every time and ensure consistency across machines, we check in generated code using a github bot.

Instead of relying on GitHub to detect changes to files we use a system of scripts and flags to detect whether the .info.version attribute of openapi.yaml has changed since last time code was generated generation. This prevents triggering on changes to the api/ folder, which causes generation to re-trigger when branches containing API spec updates were merged and could lead to merge conflicts. This led to merge conflicts if other branches were merged.

## Step 2 - Tests:

Next our CI/CD pipeline runs checks on all services to ensure functionality runs successfully. In Java, this is handled using Testcontainers to simulate the other services. Theses tests must pass for the pipeline to continue

## Step 3 - Build & Publish Images:

Our CI/CD Pipeline next uses docker to build images of all our microservices. These images are then published to GCHR.io tagged with the current commit hash, as well as a tag for the branch or PR it is generated from. If an image is created by a push to main, the latest tag is applied

## Step 4 - Deploy to Kubernetes:

If all steps succeed, our CI/CD pipeline attempts to deploy to the chair's rancher workspace using helm upgrade. In order to force images to refresh, we set the command to use the unique commit tag, rather than the lastest tag.