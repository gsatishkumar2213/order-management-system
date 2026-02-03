# Order Management System - Saga Choreography

A microservices-based order management platform demonstrating **Saga Choreography Pattern** with Spring Boot, Kafka, and PostgreSQL.

## Services

- **Order Service**: REST API for order creation and management with idempotency support
- **Payment Service**: Kafka consumer that processes payments asynchronously
- **Payment Processing Service**: Handles actual payment processing with idempotent operations
- **Inventory Service**: (Placeholder for future implementation)
- **Shipment Service**: (Placeholder for future implementation)

## Key Features

- **Saga Choreography Pattern**: Event-driven distributed transactions without a central orchestrator
- **Asynchronous Communication**: Kafka topics for loose coupling between services
- **Event-driven Architecture**: Services react to events published by other services
- **Exactly-once Semantics**: Guaranteed message delivery and processing
- **Idempotency**: Duplicate detection prevents processing the same event twice
- **PostgreSQL Persistence**: Reliable data storage across services
- **Docker Compose**: Complete end-to-end deployment

## How It Works

1. **order-service** creates an order → publishes `order-created` event to Kafka
2. **payment-service** consumes `order-created` event → processes payment → publishes `payment-completed` event
3. **order-service** consumes `payment-completed` event → updates order status to COMPLETED

Each service acts independently, reacting to events. No central orchestrator.

## Tech Stack

Spring Boot, Kafka, PostgreSQL, Docker, Docker Compose, Maven

## Running Locally

```bash
docker-compose up
```

Services will be available at:
- Order Service: http://localhost:9089
- Payment Service: http://localhost:9090
- Payment Processing Service: http://localhost:9091
- PostgreSQL: localhost:5432
- Kafka: localhost:9092

## Database

Three PostgreSQL databases (automatically created):
- `order_service_db` - Order and order items
- `payment_service_db` - Payment tracking
- `payment_processing_db` - Payment processing details

## Comparison

See **Saga Orchestration Pattern** implementation in separate repo:
- https://github.com/gsatishkumar2213/order-management-saga-orch

## Key Learnings

- **Choreography vs Orchestration**: Choreography is event-driven and loosely coupled; Orchestration provides clearer control flow
- **Idempotency**: Critical for handling message retries without duplicates
- **Event Sourcing**: Services maintain their own state based on events
- **Exactly-once Semantics**: Ensures reliable message processing in distributed systems

## Testing

Create an order:
```bash
curl -X POST http://localhost:9089/ \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "test-user",
    "amount": 100.0,
    "items": [{"itemName": "Phone", "quantity": 1}],
    "address": "123 Main St"
  }'
```

Watch the flow through logs:
```bash
docker-compose logs -f order-service
docker-compose logs -f payment-service
```

## Architecture

```
┌─────────────────┐
│  Order Service  │
│   (REST API)    │
└────────┬────────┘
         │ publishes order-created
         ▼
    ┌─────────────┐
    │    Kafka    │
    └─────────────┘
         ▲
         │ consumes order-created
         │ publishes payment-completed
         ▼
┌─────────────────────────────┐
│   Payment Service           │
│   (Choreography Listener)   │
└─────┬───────────────────────┘
      │ calls REST endpoint
      ▼
┌──────────────────────────────┐
│ Payment Processing Service   │
│ (REST Endpoint)              │
└──────────────────────────────┘
```

## Author

Satish Kumar - Building microservices with deep understanding of distributed systems patterns.