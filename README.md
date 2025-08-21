# Human Evaluation Platform (HEF) – Backend

The **Human Evaluation Platform (HEF)** backend provides the APIs, persistence,
and business logic for creating evaluation workspaces, evaluation tasks, assigning reviewers, collecting ratings, and aggregating results.

The platform is developed under the ChatMED project (https://cordis.europa.eu/project/id/101159214).

👉 Frontend UI: https://github.com/ChatMED/hef-frontend

---

## Features

- API for evaluations, assignments, reviews, and result aggregation
- Authentication/authorization
- PostgreSQL-backed persistence
- Container-friendly (Docker + Compose)
- API documentation via Swagger UI (if enabled)

> **Note on endpoints**  
> This README intentionally **does not hardcode endpoint paths**.
> Please refer to the **live Swagger UI** (see below) so the docs always match the code in this repository.

## Quickstart (Local Dev)

### Prerequisites

- Java 17+ (project uses Maven wrapper)
- Docker & Docker Compose (optional, for DB)
- Git

### Clone

```bash
git clone https://github.com/ChatMED/hef.git
cd hef
```

### Run with database via Docker Compose

```bash
# starts Postgres (and any other declared services)
docker compose up -d
```

> Check `docker-compose.yml` for service names and ports. 
> Default app port is commonly **8080** unless overridden.

### Run the API

```bash
# runs the Spring Boot app with the Maven wrapper
./mvnw spring-boot:run
```

The server will print the bound port in the console (commonly `:8080`).

---

## Configuration

Spring Boot reads properties from `src/main/resources/application.properties` 
(and environment variables). Typical database settings look like:

```properties
# example only — adjust to your setup
spring.datasource.url=jdbc:postgresql://localhost:5432/hef
spring.datasource.username=hef_user
spring.datasource.password=hef_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Other useful toggles (if included in the project):

```properties
# expose actuator endpoints if using Actuator
management.endpoints.web.exposure.include=health,info,prometheus
management.endpoint.health.show-details=when_authorized
# CORS for local UI dev
hef.cors.allowed-origins=http://localhost:3000
```

---

## API Documentation

If SpringDoc is present, open Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

(Replace the host/port if you changed them.)

You can then import this into Postman or generate a client.
