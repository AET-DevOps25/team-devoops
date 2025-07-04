## 1. 🧩 Functional Application (20 Points)

| Criteria                                                                   | Points | Status | Responsible | Comment                                                 |
| -------------------------------------------------------------------------- | ------ | ------ | ----------- | ------------------------------------------------------- |
| End-to-end functionality between all components (client, server, database) | 6      | 60%    | All         |                                                         |
| Smooth and usable user interface                                           | 4      | 80%?   | Enrico      |                                                         |
| REST API is clearly defined and matches functional needs                   | 4      | 90%    | James       | Will need some expansion for Invite/RSVP by @Anastasiia |
| Server Side has at least 3 microservices                                   | 4      | 100%   | All         |                                                         |
| Application topic is appropriately chosen and fits project objectives      | 2      | 100%   | All         |                                                         |
## 2. 🤖 GenAI Integration (10 Points)

| Criteria                                                              | Points | Status | Responsible | Comment |
| --------------------------------------------------------------------- | ------ | ------ | ----------- | ------- |
| GenAI module is well-embedded and fulfills a real user-facing purpose | 4      | %?     | Anastasiia  |         |
| Connects to cloud/local LLM                                           | 4      | %?     | Anastasiia  |         |
| Modularity of the GenAI logic as a microservice                       | 2      | %?     | Anastasiia  |         |

---

## 3. 🐳 Containerization & Local Setup (10 Points)

| Criteria                                                                                                                                | Points | Status | Responsible | Comment                     |
| --------------------------------------------------------------------------------------------------------------------------------------- | ------ | ------ | ----------- | --------------------------- |
| Each component is containerized and runnable in isolation                                                                               | 6      | 100%   | All         |                             |
| docker-compose.yml enables local development and testing with minimal effort and provides sane defaults (no complex env setup required) | 4      | 90%    | James       | Requires testing at the end |

---

## 4. 🔁 CI/CD & Deployment (20 Points)

| Criteria                                                                                                                | Points | Status | Responsible                                                                                                      | Comment                                                                 |
| ----------------------------------------------------------------------------------------------------------------------- | ------ | ------ | ---------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------- |
| CI pipeline with build, test, and Docker image generation via GitHub Actions                                            | 8      | 80%    | James (6):<br>- Build,<br>- Publish<br>- Unit Testing<br><br>Open (2):<br>- Integration Testing<br>(Anastasiia?) | Missing integration testing<br>                                         |
| CD pipeline set up to automatically deploy to Kubernetes on main merge                                                  | 6      | 90%*   | Anastasiia                                                                                                       | Needs work to match current implementation                              |
| Deployment works on our infrastructure or Cloud, alternative Kubernetes environments (e.g., Minikube, TUM infra, Azure) | 6      | %?     | Enrico (AWS)                                                                                                     | It deploys to Kubernetes on Rancher, is that enough or do we need more? |

---

## 5. 📊 Monitoring & Observability (10 Points)

| Criteria                                                | Points | Status | Responsible | Comment |
| ------------------------------------------------------- | ------ | ------ | ----------- | ------- |
| Prometheus integrated and collecting meaningful metrics | 4      | %?     | Enrico      |         |
| Grafana dashboards for system behavior visualization    | 4      | %?     | Enrico      |         |
| At least one alert rule set up                          | 2      | %?     | Enrico      |         |

---

## 6. 🧪 Testing & Structured Engineering Process (20 Points)

| Criteria                                                                                                                                                             | Points | Status | Responsible                                                                                                                                                   | Comment                                                    |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------ | ------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------- |
| Test cases implemented for server/client and GenAI logic                                                                                                             | 6      | 30%    | Unit: <br><br>James (2):<br>- User<br>- Matching<br><br>Anastasiia (2)<br>- Gateway<br>- RSVP<br>- GenAI<br><br>Integration:<br>- Open (2)<br>- (Anastasiia?) |                                                            |
| Evidence of software engineering process: documented requirements, architecture models, such as top-level architecture, use case diagramm and analysis object model. | 10     | -      | All                                                                                                                                                           | Requires update at the end                                 |
| Tests run automatically in CI and cover key functionality                                                                                                            | 4      | 50%    | James                                                                                                                                                         | Units Run automatically.<br><br>Work needed on Integration |

---

## 7. 📚 Documentation & Weekly Reporting (10 Points)

| Criteria                                                                                                                                               | Points | Status | Responsible           | Comment                                      |
| ------------------------------------------------------------------------------------------------------------------------------------------------------ | ------ | ------ | --------------------- | -------------------------------------------- |
| [README.md](http://README.md) or Wiki includes setup instructions, architecture overview, usage guide, and a clear mapping of student responsibilities | 2      | %?     | Open<br>(James?)      | Needs update at end<br><br>Upload this table |
| Documentation of CI/CD setup, and GenAI usage included                                                                                                 | 2      | %?     | Open<br>(James)?      | Needs update at end                          |
| Deployment and local setup instructions are clear, reproducible, and platform-specific (≤3 commands for local setup, with sane defaults)               | 2      | %?     | Open<br>(Anastasiia?) | Needs update at end                          |
| Subsystems have documented interfaces (API-driven deployment, e.g. Swagger/OpenAPI)                                                                    | 2      | 100%   | James                 | May need update                              |
| Monitoring instructions included in the documentation and exported as files                                                                            | 2      | %?     | Enrico                | Unsure what this means                       |

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
| Advanced Kubernetes use (e.g., self-healing, custom operators, auto-scaling) | +1     | ?      | Anastasiia? | How hard is this to implement                                  |
| Full RAG pipeline implementation (with vector DB like Weaviate)              | +1     | ?      | Anastasiia? | Part of GenAI.<br><br>Is this even applicable to our use-case? |
| Real-world-grade observability (e.g., log aggregation, tracing)              | +1     | ?      | Enrico?     | How hard is this to implement                                  |
| Beautiful, original UI or impactful project topic                            | +1     | %?     | Enrico      | This is pretty much done no?                                   |

## Total Points

| Responsible | Current | Including Open |
| ----------- | ------- | -------------- |
| All         | 28      | 28             |
| Enrico      | 22      | 24             |
| James       | 22      | 26             |
| Anastasiia  | 18      | 26             |
| Open        | 10      | 0              |
| Extra       | 4       | 0              |
