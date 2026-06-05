# E-Commerce Microservices Platform

A production-ready Spring Boot microservices application demonstrating enterprise-grade architecture, distributed systems, and REST API development.

## 🏗️ Architecture

┌─────────────────────────────────────────────────────┐
│              API Gateway (Port 8080)                 │
│         (Spring Cloud Gateway - Reactive)           │
└────────────────────┬────────────────────────────────┘
│
┌────────────┼────────────┐
│            │            │
┌────▼────┐  ┌────▼────┐ ┌────▼────┐
│  User   │  │ Product │ │  Order  │
│ Service │  │ Service │ │ Service │
│ (8001)  │  │ (8002)  │ │ (8003)  │
└────┬────┘  └────┬────┘ └────┬────┘
│            │           │
└────────────┼───────────┘
│
┌────────────▼────────────┐
│  Eureka Server (8761)   │
│  Service Discovery      │
└─────────────────────────┘
│
┌────────────▼────────────┐
│    MySQL Database       │
│  (user_db, product_db,  │
│      order_db)          │
└─────────────────────────┘

## ✨ Features Implemented

### Day 1: JWT Authentication ✅
- JWT token generation & validation using JJWT library
- Secure login endpoint returning JWT tokens
- Protected endpoints requiring valid JWT tokens
- Spring Security integration with JWT filters
- Feign client interceptor for inter-service JWT token passing

### Day 2: Global Exception Handling ✅
- Custom exception classes (ResourceNotFoundException, BadRequestException, UnauthorizedException)
- Centralized exception handler using @RestControllerAdvice
- Consistent error response format across all services
- Proper HTTP status codes (400, 401, 403, 404, 500)
- Request path tracking in error responses

### Core Features
- **Microservices Architecture**: 5 independent Spring Boot services
- **Service Discovery**: Eureka server for automatic service registration
- **Inter-Service Communication**: OpenFeign with JWT token forwarding
- **Database**: MySQL with JPA/Hibernate ORM
- **REST APIs**: Complete CRUD operations for users, products, and orders
- **Business Logic**: Order creation with user/product validation and inventory management

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Framework | Spring Boot 3.1.5 |
| Language | Java 17 |
| Build Tool | Maven |
| Service Discovery | Eureka Server |
| API Gateway | Spring Cloud Gateway |
| Authentication | JWT (JJWT) |
| Databases | MySQL |
| ORM | JPA/Hibernate |
| Serialization | Jackson |
| Networking | OpenFeign, Eureka Client |
| Security | Spring Security 7.0.5 |

## 📁 Project Structure

Ecommerce-Microservices/
├── eureka-server/              # Service Discovery
├── user-service/               # User Management
│   ├── controller/             # REST endpoints
│   ├── service/                # Business logic
│   ├── repository/             # Data access
│   ├── model/                  # JPA entities
│   ├── security/               # JWT & Auth filters
│   ├── config/                 # Spring configuration
│   └── exception/              # Global exception handling
├── product-service/            # Product Catalog
├── order-service/              # Order Management
├── api-gateway/                # Request routing
└── docker-compose.yml          # Containerization (upcoming)

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+
- Git

### Installation

1. **Clone the repository**
```bash
   git clone https://github.com/Mr-ShannuKukke/Ecommerce-Microservices.git
   cd Ecommerce-Microservices
```

2. **Setup MySQL Database**
```bash
   mysql -u root -p
   CREATE DATABASE user_db;
   CREATE DATABASE product_db;
   CREATE DATABASE order_db;
```

3. **Update database credentials** (if different)
   - Edit `application.yml` in each service
   - Update `spring.datasource.username` and `spring.datasource.password`

4. **Build all services**
```bash
   # Eureka Server
   cd eureka-server
   ./mvnw clean install
   ./mvnw spring-boot:run
   
   # User Service (new terminal)
   cd user-service
   ./mvnw clean install
   ./mvnw spring-boot:run
   
   # Product Service (new terminal)
   cd product-service
   ./mvnw clean install
   ./mvnw spring-boot:run
   
   # Order Service (new terminal)
   cd order-service
   ./mvnw clean install
   ./mvnw spring-boot:run
   
   # API Gateway (new terminal)
   cd api-gateway
   ./mvnw clean install
   ./mvnw spring-boot:run
```

### Verify Services

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080

## 📚 API Endpoints

### User Service (via Gateway)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/users/register` | Register new user | ❌ |
| POST | `/api/users/login` | Login & get JWT token | ❌ |
| GET | `/api/users/{id}` | Get user by ID | ✅ |
| GET | `/api/users/email/{email}` | Get user by email | ✅ |
| PUT | `/api/users/{id}` | Update user | ✅ |
| DELETE | `/api/users/{id}` | Delete user | ✅ |

### Product Service (via Gateway)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/products` | Create product | ✅ |
| GET | `/api/products` | Get all products | ✅ |
| GET | `/api/products/{id}` | Get product by ID | ✅ |
| PUT | `/api/products/{id}` | Update product | ✅ |
| DELETE | `/api/products/{id}` | Delete product | ✅ |

### Order Service (via Gateway)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/orders` | Create order | ✅ |
| GET | `/api/orders` | Get all orders | ✅ |
| GET | `/api/orders/{id}` | Get order by ID | ✅ |
| GET | `/api/orders/user/{userId}` | Get user's orders | ✅ |

## 🔐 Authentication Example

### 1. Register User
```bash
POST http://localhost:8080/api/users/register
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123",
  "name": "John Doe",
  "phoneNumber": "9876543210"
}
```

### 2. Login
```bash
POST http://localhost:8080/api/users/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}

Response:
{
  "message": "Login successful",
  "userId": 1,
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

### 3. Use Token for Protected Endpoint
```bash
GET http://localhost:8080/api/users/1
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

## 📋 Complete Order Flow

1. **Register & Login** → Get JWT token
2. **Create Product** → With valid token
3. **Create Order** → System validates:
   - User exists (calls User Service)
   - Product exists (calls Product Service)
   - Inventory available (calls Product Service)
   - Reduces product quantity automatically

## 🧪 Testing

All endpoints tested with:
- ✅ JWT authentication
- ✅ Inter-service communication
- ✅ Exception handling
- ✅ Error responses

Use Postman or cURL to test the APIs.

## 🔄 Data Flow Example

Client Request (with JWT)
↓
API Gateway (8080)
↓
Order Service (8003)
├→ Calls User Service (8001) [with JWT]
├→ Calls Product Service (8002) [with JWT]
└→ Saves Order to MySQL
↓
Response to Client

## 📦 Upcoming Enhancements

- [ ] Docker & Docker Compose setup
- [ ] Unit & Integration tests
- [ ] Spring Cloud Sleuth for logging
- [ ] Resilience4j for circuit breaker
- [ ] AWS deployment
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Performance optimization
- [ ] Caching strategy

## 🤝 Contributing

This is a learning project. Feel free to fork and explore!

## 📄 License

MIT License

## 👨‍💻 Author

Shannu Kukke
- GitHub: https://github.com/Mr-ShannuKukke
- Email: your-email@example.com

## 📝 Notes

- All services require valid JWT token in `Authorization: Bearer <token>` header
- Database tables are auto-created by Hibernate (ddl-auto: update)
- Services auto-register with Eureka on startup
- Feign client automatically resolves service URLs via Eureka

---

**Status**: ✅ Production-Ready (MVP)
**Last Updated**: June 5, 2026
**Version**: 1.0.0
