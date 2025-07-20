## 1. 🧩 Functional Application (20 Points)

| Criteria                                                                   | Points | Status | Responsible | Comment |
| -------------------------------------------------------------------------- | ------ | ------ | ----------- | ------- |
| End-to-end functionality between all components (client, server, database) | 6      | 100%   | All         |         |
| Smooth and usable user interface                                           | 4      | 100%   | Enrico      |         |
| REST API is clearly defined and matches functional needs                   | 4      | 100%   | James       |         |
| Server Side has at least 3 microservices                                   | 4      | 100%   | All         |         |
| Application topic is appropriately chosen and fits project objectives      | 2      | 100%   | All         |         |
## 2. 🤖 GenAI Integration (10 Points)

| Criteria                                                              | Points | Status | Responsible | Comment |
| --------------------------------------------------------------------- | ------ | ------ | ----------- | ------- |
| GenAI module is well-embedded and fulfills a real user-facing purpose | 4      | 100%   | Anastasiia  |         |
| Connects to cloud/local LLM                                           | 4      | 100%   | Anastasiia  |         |
| Modularity of the GenAI logic as a microservice                       | 2      | 100%   | Anastasiia  |         |

---

## 3. 🐳 Containerization & Local Setup (10 Points)

| Criteria                                                                                                                                | Points | Status | Responsible | Comment |
| --------------------------------------------------------------------------------------------------------------------------------------- | ------ | ------ | ----------- | ------- |
| Each component is containerized and runnable in isolation                                                                               | 6      | 100%   | All         |         |
| docker-compose.yml enables local development and testing with minimal effort and provides sane defaults (no complex env setup required) | 4      | 100%   | James       |         |

---

## 4. 🔁 CI/CD & Deployment (20 Points)

| Criteria                                                                                                                | Points | Status | Responsible                                        | Comment |
| ----------------------------------------------------------------------------------------------------------------------- | ------ | ------ | -------------------------------------------------- | ------- |
| CI pipeline with build, test, and Docker image generation via GitHub Actions                                            | 8      | 100%   | James (4):<br><br>Anastasiia (2)<br><br>Enrico (2) | <br>    |
| CD pipeline set up to automatically deploy to Kubernetes on main merge                                                  | 6      | 100%   | Anastasiia                                         |         |
| Deployment works on our infrastructure or Cloud, alternative Kubernetes environments (e.g., Minikube, TUM infra, Azure) | 6      | 100%   | Enrico (AWS)                                       |         |

---

## 5. 📊 Monitoring & Observability (10 Points)

| Criteria                                                | Points | Status | Responsible | Comment                                                            |
| ------------------------------------------------------- | ------ | ------ | ----------- | ------------------------------------------------------------------ |
| Prometheus integrated and collecting meaningful metrics | 4      | 80%*   | Enrico      | Unable to deploy to Rancher due to permissions issues with Grafana |
| Grafana dashboards for system behavior visualization    | 4      | 100%   | Enrico      |                                                                    |
| At least one alert rule set up                          | 2      | 100%   | Enrico      |                                                                    |

---

## 6. 🧪 Testing & Structured Engineering Process (20 Points)

| Criteria                                                                                                                                                             | Points | Status | Responsible                                                                                        | Comment |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------ | ------ | -------------------------------------------------------------------------------------------------- | ------- |
| Test cases implemented for server/client and GenAI logic                                                                                                             | 6      | 100%   | James (2):<br>- User<br>- Matching<br><br>Anastasiia (2):<br>- GenAI<br><br>Enrico(2):<br>- Client |         |
| Evidence of software engineering process: documented requirements, architecture models, such as top-level architecture, use case diagramm and analysis object model. | 10     | 100%   | All                                                                                                |         |
| Tests run automatically in CI and cover key functionality                                                                                                            | 4      | 100%   | James                                                                                              |         |

---

## 7. 📚 Documentation & Weekly Reporting (10 Points)

| Criteria                                                                                                                                               | Points | Status | Responsible | Comment |
| ------------------------------------------------------------------------------------------------------------------------------------------------------ | ------ | ------ | ----------- | ------- |
| [README.md](http://README.md) or Wiki includes setup instructions, architecture overview, usage guide, and a clear mapping of student responsibilities | 2      | 100%   | James       |         |
| Documentation of CI/CD setup, and GenAI usage included                                                                                                 | 2      | 100%   | Anastassia  |         |
| Deployment and local setup instructions are clear, reproducible, and platform-specific (≤3 commands for local setup, with sane defaults)               | 2      | 100%   | Anastasiia  |         |
| Subsystems have documented interfaces (API-driven deployment, e.g. Swagger/OpenAPI)                                                                    | 2      | 100%   | James       |         |
| Monitoring instructions included in the documentation and exported as files                                                                            | 2      | 100%   | Enrico      |         |

---

## 8. 🎤 Final Presentation (Pass/Fail)

| Criteria                                                                                  | Points    | Status |
| ----------------------------------------------------------------------------------------- | --------- | ------ |
| All students present their own subsystem                                                  | Pass/Fail | Open   |
| Live demo of application and DevOps setup                                                 | Pass/Fail | Open   |
| Team reflects on what worked well, what didn’t, and answers follow-up technical questions | Pass/Fail | Open   |

---

## 🏅 Bonus Points (up to +5)

| Criteria                                                                     | Points | Status | Responsible | Comment                                                        |
| ---------------------------------------------------------------------------- | ------ | ------ | ----------- | -------------------------------------------------------------- |
| Advanced Kubernetes use (e.g., self-healing, custom operators, auto-scaling) | +1     | 0%     |             |                                                                |
| Full RAG pipeline implementation (with vector DB like Weaviate)              | +1     | 0%     |             |  Not applicable                                                |
| Real-world-grade observability (e.g., log aggregation, tracing)              | +1     | 50%    | Enrico      |  Limited by grafana permission issues                          |
| Beautiful, original UI or impactful project topic                            | +1     | 100%   | Enrico      | In our opinion                                                 |