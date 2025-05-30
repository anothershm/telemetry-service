# Telemetry Service (CQRS + Hexagonal Architecture)

This project implements a telemetry ingestion system using **Spring Boot**, **Kafka**, **PostgreSQL**, and **CQRS** with **Hexagonal Architecture**.
Originally created as a technical exercise.

## 🧱 Architecture Overview

- **Hexagonal Architecture**: Separates application core (domain) from adapters (REST, persistence, messaging).
- **CQRS**: Maybe I didn't use the exact CQRS pattern, but the idea is to separate the command (write) and query (read) sides of the application.
- **Kafka**: Used to publish telemetry events asynchronously.
- **PostgreSQL**: Persists raw telemetry data and device status projections.

```
Client --> REST API (Controller) --> Command Handler --> Persistence + Event Publisher --> Kafka --> Consumer --> Projection Updater --> Device Status Table
```

---

## 📦 Modules

- `telemetry`: Exposes HTTP endpoints and handles commands
- `projection`: Listens to Kafka and updates device status projections
- `shared-event`: Contains the shared event for kafka and is used by both modules

---

## 🚀 Getting Started

### Prerequisites
- Java 17
- Maven
- Docker & Docker Compose

### Run services + infrastructure

First build the project
```bash
mvn clean install
```
You can start both services in parallel using the helper script from the **root** folder:

```bash
infra/start-all.sh
```

> By default:
> - PostgreSQL (port `5432`)
> - Kafka + Zookeeper (port `9092`)
> - Telemetry runs on `http://localhost:8080`
> - Projection runs on `http://localhost:8081`

### Remember to kill the processes

Run this if you want to stop both applications:

```bash
lsof -ti :8080 | xargs kill -9
lsof -ti :8081 | xargs kill -9
```

And remember to stop docker as well.

```bash
cd infra
 docker compose -f docker-compose.yml down
```

---

## 🧪 Testing the Flow

### Send telemetry
```bash
curl -X POST http://localhost:8080/telemetry \
  -H "Content-Type: application/json" \
  -d '{ "deviceId": 1, "measurement": 12, "date": "2025-01-31T13:00:00" }'
```

Repeat with different devices and timestamps.

### Get latest device statuses
```bash
curl http://localhost:8081/device-status
```

Returns:
```json
[
  { "deviceId": 1, "measurement": 12, "date": "2025-01-31T13:00:00" }
]
```
---

## 🧪 Postman Testing

You can use the provided `TestSuit.json` in `postman/` to test:
- Valid and invalid telemetry
- Edge cases
- Final status projection

---

## 🧠 Edge Case Handling

- **Duplicate events**: Processed but do not overwrite newer data
- **Out-of-order telemetry**: Ignored if timestamp is older than current status
- **Validation**: Handled via `@Valid` annotations in DTOs

---

## ✅ Features Implemented

- [x] Hexagonal Architecture
- [x] CQRS: Command + CommandHandler + Projection
- [x] Kafka event publishing and consumption
- [x] PostgreSQL for raw + projection persistence
- [x] REST API for ingestion and querying
- [x] Docker Compose for Kafka + DB
- [x] Postman collection for manual testing

---

## 📂 Folder Structure

```
telemetry-service/
├── telemetry/             # Spring Boot API that receives telemetry data
├── projection/            # Kafka consumer & projection
├── infra/                 # Docker Compose setup
├── postman/               # Postman test collection
└── README.md
```

---

## 🧩 Future Improvements
Some potential improvements and extensions:

- ⛑️ **Resilience enhancements**: Add retry/backoff logic to the Kafka consumer.
- ⚖️ **Idempotency strategies**: Use event hashes or unique IDs to safely handle duplicates.
- 🌍 **Timezone normalization**: Enforce UTC conversion and display logic for international support.
- 🔐 **Security layer**: Add OAuth2 or another authentication mechanism to ensure data integrity and prevent tampering.
- 📊 **Metrics & observability**: : Integrate more detailed logs and metrics to feed into systems like Grafana or Datadog for alerting and visibility.
- ☁️ **Cloud deployment**: Package everything into containers and deploy via Kubernetes or another cloud-native stack.
  
---

## 🧠 Assumptions & Clarifications

During implementation, the following assumptions were made for simplification of the exercise:

- **Out-of-order telemetry**  
  If a telemetry event arrives with a timestamp **older** than the current projection, it is **ignored** and does **not update** the device status.

- **Duplicate telemetry**  
  If an identical telemetry event is sent more than once (same deviceId, measurement, and date), it is **accepted** and processed again. Projection logic ensures it won't cause inconsistencies.

- **No authentication or authorization**  
  The API is open — assumed to be internal or behind a gateway.

- **Device IDs are integers**  
  No UUIDs or device registration logic; assumed incoming telemetry is from known devices.

- **Time zone handling**  
  Dates are expected in ISO-8601 format. No timezone conversion is applied — assumed timestamps are UTC.

- **No deduplication at Kafka level**  
  Duplicates are handled at the **consumer level only**, based on timestamp logic.

- **PostgreSQL is used for both raw telemetry and projections**  
  A single DB setup was assumed for simplicity.

---

## ⏱️ Time Spent

- ~1h: Project setup, Hexagonal structure, initial POST endpoint
- ~1h: Refactor into modular structure, create consumer with simulated projection updates
- ~1h: Full Kafka integration with PostgreSQL
- ~1h: Manual testing, Postman collection
- ~2h: Troubleshooting kafka and context errors, fixing issues, and final touches
- ~1h: Git upload, renaming temperature → measurement, and retesting everything multiple times because I’m a lunatic 🤡

**Total: ~7h**
---

## 📞 Contact

Built with 🐱 & 🍵 by [Sara Henriques](https://www.linkedin.com/in/sarahenriques-dev/)  
