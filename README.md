# Energy Community Project

## Repository
https://github.com/phiwa94/energy-community.git


---

## How to Run the Project

Start Docker infrastructure first:

```bash
docker compose up -d
```

Then start the backend services. Each component is its own application and can be started independently.

Recommended startup order:

```text
1. RabbitMQ and PostgreSQL through Docker Compose
2. energy-community-usage-service
3. energy-community-percentage-service
4. energy-community-rest
5. energy-community-ui
6. energy-community-producer and/or energy-community-user
```

You can also publish test messages manually through the RabbitMQ Management UI.

---

## Infrastructure

RabbitMQ and PostgreSQL are started with Docker Compose.

Start infrastructure from the project root:

```bash
docker compose up -d
```

Stop infrastructure:

```bash
docker compose down
```

RabbitMQ Management UI:

```text
http://localhost:15672
```

Login:

```text
username: guest
password: guest
```

PostgreSQL connection:

```text
host: localhost
port: 5432
database: energy_community
username: energy_user
password: energy_password
```
---

## Manual RabbitMQ Test

With Docker, the usage service, percentage service, and REST API running, open RabbitMQ Management:

```text
http://localhost:15672
```

Publish this message to `community_energy_producer_out`:

```json
{
  "type": "producer",
  "association": "community",
  "kwh": 5.0,
  "datetime": "2026-06-06T16:20:00"
}
```

Publish this message to `community_energy_user_out`:

```json
{
  "type": "user",
  "association": "community",
  "kwh": 10.0,
  "datetime": "2026-06-06T16:30:00"
}
```

Expected usage result:

```text
communityProduced = 5.0
communityUsed = 5.0
gridUsed = 5.0
```

Expected percentage result:

```text
communityDepleted = 100.0
gridPortion = 50.0
```
---
## REST API

### Current Energy Data

```text
GET http://localhost:8080/energy/current
```

### Historical Usage Data

```text
GET http://localhost:8080/energy/historical?start=2026-06-06T14:00:00&end=2026-06-06T17:00:00
```
--- 

## System Overview

The project consists of several independently startable components:

```text
energy-community-percentage-service
energy-community-producer
energy-community-rest
energy-community-ui
energy-community-usage-service
energy-community-user
```

The full system flow is:

```text
Producer/User
→ RabbitMQ
→ Usage Service
→ PostgreSQL usage_data
→ Percentage Service
→ PostgreSQL current_percentage
→ REST API
→ JavaFX GUI
```
---

## Features

- Fetch current energy data
- Filter historical data by date and time
- Input validation in the UI
- Error handling for invalid input and connection issues
---

## Notes

- The database is PostgreSQL and is started through Docker Compose.
- Tables are created/updated automatically through JPA using `spring.jpa.hibernate.ddl-auto=update`.
- The REST API reads from the database but does not write to it.
- The GUI communicates only with the REST API.