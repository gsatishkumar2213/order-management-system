# Order Management System

A microservices-based order management platform built with Spring Boot and Apache Kafka. 
The system demonstrates distributed transaction patterns, event-driven architecture, and 
exactly-once semantics for reliable message processing.

**Services:**
- **Order Service**: REST API for order management
- **Payment Service**: Payment processing and management
- **Payment Processing Service**: Kafka consumer implementing exactly-once semantics with idempotency keys
- **Inventory Service**: Inventory management
- **Shipment Service**: Shipment tracking

**Key Features:**
- Event-driven architecture using Apache Kafka
- Saga pattern for distributed transactions
- Exactly-once message processing guarantees
- Containerized with Docker and orchestrated with Kubernetes/Docker Compose
- Comprehensive error handling and validation

**Tech Stack:** Spring Boot, Kafka, PostgreSQL, Docker, Kubernetes
