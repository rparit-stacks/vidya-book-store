# API Documentation

## Overview

This document provides a comprehensive reference for all REST API endpoints in the Vidyarthi Book Depot platform. All APIs follow RESTful conventions and return JSON responses.

## Base URL

```
Development: http://localhost:8080/api
Production: https://api.vidyarthibookdepot.com/api
```

## Authentication

Most endpoints require JWT authentication. Include the token in the Authorization header:

```
Authorization: Bearer {jwt_token}
```

## Common Response Formats

### Success Response

```json
{
  "data": { ... },
  "message": "Success message",
  "timestamp": "2025-12-23T10:00:00Z"
}
```

### Error Response

```json
{
  "timestamp": "2025-12-23T10:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/inquiries",
  "details": [
    {
      "field": "subject",
      "message": "Subject is required"
    }
  ]
}
```

### Paginated Response

```json
{
  "content": [ ... ],
  "page": 0,
  "size": 20,
  "totalElements": 100,
  "totalPages": 5,
  "first": true,
  "last": false
}
```

## HTTP Status Codes

- `200 OK`: Successful request
- `201 Created`: Resource created successfully
- `204 No Content`: Successful deletion
- `400 Bad Request`: Invalid request data
- `401 Unauthorized`: Authentication required
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Resource not found
- `409 Conflict`: Resource conflict (e.g., duplicate)
- `500 Internal Server Error`: Server error

## API Endpoints

### Authentication APIs

#### Register User

```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "customer@example.com",
  "phone": "+919876543210",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response**: `201 Created`

#### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "customer@example.com",
  "password": "SecurePassword123!"
}
```

**Response**: `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 1,
    "email": "customer@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CUSTOMER"
  }
}
```

#### Refresh Token

```http
POST /api/auth/refresh
Authorization: Bearer {token}
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response**: `200 OK`

### User APIs

#### Get Current User Profile

```http
GET /api/users/profile
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Update User Profile

```http
PUT /api/users/profile
Authorization: Bearer {token}
Content-Type: application/json

{
  "phone": "+919876543211",
  "firstName": "Jane",
  "lastName": "Smith"
}
```

**Response**: `200 OK`

#### Get User by ID (Owner Only)

```http
GET /api/users/{id}
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Update User Role (Owner Only)

```http
PUT /api/users/{id}/role
Authorization: Bearer {token}
Content-Type: application/json

{
  "role": "OWNER"
}
```

**Response**: `200 OK`

#### List All Users (Owner Only)

```http
GET /api/users?page=0&size=20&role=CUSTOMER&enabled=true
Authorization: Bearer {token}
```

**Response**: `200 OK` (Paginated)

### Product APIs

#### List Products

```http
GET /api/products?page=0&size=20&categoryId=1&board=SSC&class=10&search=mathematics
```

**Response**: `200 OK` (Paginated)

#### Get Product Details

```http
GET /api/products/{id}
```

**Response**: `200 OK`

#### Search Products

```http
GET /api/products/search?q=mathematics&page=0&size=20
```

**Response**: `200 OK` (Paginated)

#### Get Products by Category

```http
GET /api/products/category/{categoryId}?page=0&size=20
```

**Response**: `200 OK` (Paginated)

#### Get Products by Board

```http
GET /api/products/board/{board}?page=0&size=20
```

**Response**: `200 OK` (Paginated)

#### Get Products by Class

```http
GET /api/products/class/{class}?page=0&size=20
```

**Response**: `200 OK` (Paginated)

#### Create Product (Owner Only)

```http
POST /api/products
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Physics for Class 12",
  "description": "Advanced physics textbook",
  "isbn": "978-1234567891",
  "author": "Dr. Suresh Patel",
  "publisher": "Science Publishers",
  "price": 550.00,
  "categoryId": 1,
  "board": "HSC",
  "class": "12",
  "featured": false,
  "bestseller": true,
  "imageUrl": "/images/products/physics-12.jpg"
}
```

**Response**: `201 Created`

#### Update Product (Owner Only)

```http
PUT /api/products/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Updated Product Name",
  "price": 600.00
}
```

**Response**: `200 OK`

#### Delete Product (Owner Only)

```http
DELETE /api/products/{id}
Authorization: Bearer {token}
```

**Response**: `204 No Content`

#### Get Featured Products

```http
GET /api/products/featured?limit=10
```

**Response**: `200 OK`

#### Get Bestseller Products

```http
GET /api/products/bestsellers?limit=10
```

**Response**: `200 OK`

#### Get Trending Products

```http
GET /api/products/trending?limit=10&period=MONTH
```

**Response**: `200 OK`

### Category APIs

#### List All Categories

```http
GET /api/categories?includeSubcategories=false
```

**Response**: `200 OK`

#### Get Category by ID

```http
GET /api/categories/{id}
```

**Response**: `200 OK`

#### Get Category Tree

```http
GET /api/categories/tree
```

**Response**: `200 OK`

#### Create Category (Owner Only)

```http
POST /api/categories
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Competitive Exams",
  "description": "Books for competitive exam preparation",
  "parentCategoryId": null
}
```

**Response**: `201 Created`

#### Update Category (Owner Only)

```http
PUT /api/categories/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Updated Category Name",
  "description": "Updated description"
}
```

**Response**: `200 OK`

#### Delete Category (Owner Only)

```http
DELETE /api/categories/{id}
Authorization: Bearer {token}
```

**Response**: `204 No Content`

### Inquiry APIs

#### Create Inquiry

```http
POST /api/inquiries
Authorization: Bearer {token}
Content-Type: application/json

{
  "type": "BOOK_AVAILABILITY",
  "subject": "Availability of Mathematics Class 10",
  "message": "Do you have Mathematics for Class 10 in stock?",
  "productId": 1
}
```

**Response**: `201 Created`

#### List Inquiries

```http
GET /api/inquiries?page=0&size=20&status=PENDING&type=BOOK_AVAILABILITY
Authorization: Bearer {token}
```

**Response**: `200 OK` (Paginated)

#### Get Inquiry Details

```http
GET /api/inquiries/{id}
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Update Inquiry Status (Owner Only)

```http
PUT /api/inquiries/{id}/status
Authorization: Bearer {token}
Content-Type: application/json

{
  "status": "RESOLVED"
}
```

**Response**: `200 OK`

#### Add Response to Inquiry (Owner Only)

```http
POST /api/inquiries/{id}/response
Authorization: Bearer {token}
Content-Type: application/json

{
  "message": "Yes, we have this book in stock."
}
```

**Response**: `201 Created`

#### Get My Inquiries

```http
GET /api/inquiries/my-inquiries?page=0&size=20&status=PENDING
Authorization: Bearer {token}
```

**Response**: `200 OK` (Paginated)

#### Get Pending Inquiries (Owner Only)

```http
GET /api/inquiries/pending?page=0&size=20
Authorization: Bearer {token}
```

**Response**: `200 OK` (Paginated)

#### Get Inquiry Statistics (Owner Only)

```http
GET /api/inquiries/statistics?period=MONTH
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Update Inquiry

```http
PUT /api/inquiries/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "subject": "Updated subject",
  "message": "Updated message"
}
```

**Response**: `200 OK`

#### Delete Inquiry

```http
DELETE /api/inquiries/{id}
Authorization: Bearer {token}
```

**Response**: `204 No Content`

### Chat APIs

#### Create or Get Conversation

```http
POST /api/chat/conversations
Authorization: Bearer {token}
Content-Type: application/json

{}
```

**Response**: `200 OK` or `201 Created`

#### List User's Conversations

```http
GET /api/chat/conversations?page=0&size=20
Authorization: Bearer {token}
```

**Response**: `200 OK` (Paginated)

#### Get Conversation with Messages

```http
GET /api/chat/conversations/{id}?page=0&size=50
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Send Message

```http
POST /api/chat/conversations/{id}/messages
Authorization: Bearer {token}
Content-Type: application/json

{
  "message": "Hello, do you have Mathematics Class 10?"
}
```

**Response**: `201 Created`

#### Get Messages

```http
GET /api/chat/conversations/{id}/messages?page=0&size=50
Authorization: Bearer {token}
```

**Response**: `200 OK` (Paginated)

#### Mark Message as Read

```http
PUT /api/chat/messages/{id}/read
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Get Unread Message Count

```http
GET /api/chat/unread-count
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Get All Conversations (Owner Only)

```http
GET /api/chat/conversations/owner/all?page=0&size=20&unreadOnly=false
Authorization: Bearer {token}
```

**Response**: `200 OK` (Paginated)

### Notification APIs

#### Get User Notifications

```http
GET /api/notifications?page=0&size=20&read=false&type=INQUIRY_RESPONSE
Authorization: Bearer {token}
```

**Response**: `200 OK` (Paginated)

#### Mark Notification as Read

```http
PUT /api/notifications/{id}/read
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Mark All Notifications as Read

```http
PUT /api/notifications/read-all
Authorization: Bearer {token}
```

**Response**: `200 OK`

#### Get Unread Notification Count

```http
GET /api/notifications/unread-count
Authorization: Bearer {token}
```

**Response**: `200 OK`

## WebSocket API

### Connection

```javascript
const socket = new SockJS('http://localhost:8080/ws/chat');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    
    // Subscribe to conversation messages
    stompClient.subscribe('/topic/chat/1', function(message) {
        const chatMessage = JSON.parse(message.body);
        console.log('Received:', chatMessage);
    });
});
```

### Send Message via WebSocket

```javascript
stompClient.send("/app/chat/send", {}, JSON.stringify({
    conversationId: 1,
    message: "Hello, do you have Mathematics Class 10?"
}));
```

## Rate Limiting

API endpoints are rate-limited to prevent abuse:

- **Authentication endpoints**: 5 requests per minute per IP
- **General endpoints**: 100 requests per minute per user
- **Chat endpoints**: 30 messages per minute per user

Rate limit headers:
```
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1703328600
```

## Error Handling

### Validation Errors

When request validation fails:

```json
{
  "timestamp": "2025-12-23T10:00:00Z",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid request data",
  "details": [
    {
      "field": "email",
      "message": "Email is required"
    },
    {
      "field": "password",
      "message": "Password must be at least 8 characters"
    }
  ]
}
```

### Authentication Errors

```json
{
  "timestamp": "2025-12-23T10:00:00Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

### Authorization Errors

```json
{
  "timestamp": "2025-12-23T10:00:00Z",
  "status": 403,
  "error": "Forbidden",
  "message": "You do not have permission to access this resource"
}
```

### Not Found Errors

```json
{
  "timestamp": "2025-12-23T10:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found",
  "path": "/api/products/999"
}
```

## API Versioning

Currently, all APIs are unversioned. Future versions will use:

```
/api/v1/products
/api/v2/products
```

## CORS Configuration

CORS is enabled for the following origins:

- Development: `http://localhost:3000`
- Production: `https://vidyarthibookdepot.com`

## Request/Response Examples

### Complete Product Creation Flow

```bash
# 1. Login as owner
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "owner@vbd.com",
    "password": "password123"
  }'

# Response contains JWT token
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 2. Create product
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mathematics for Class 10",
    "description": "Comprehensive mathematics textbook",
    "isbn": "978-1234567890",
    "author": "Dr. Ramesh Kumar",
    "publisher": "Educational Publishers",
    "price": 450.00,
    "categoryId": 1,
    "board": "SSC",
    "class": "10",
    "featured": true
  }'
```

### Complete Inquiry Flow

```bash
# 1. Customer creates inquiry
curl -X POST http://localhost:8080/api/inquiries \
  -H "Authorization: Bearer $CUSTOMER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "type": "BOOK_AVAILABILITY",
    "subject": "Availability of Mathematics Class 10",
    "message": "Do you have this book in stock?",
    "productId": 1
  }'

# 2. Owner responds
curl -X POST http://localhost:8080/api/inquiries/1/response \
  -H "Authorization: Bearer $OWNER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Yes, we have this book in stock."
  }'
```

## Testing

### Postman Collection

A Postman collection is available with all API endpoints pre-configured.

### API Testing Tools

- Postman
- cURL
- HTTPie
- Swagger UI (available at `/swagger-ui.html`)

## Support

For API support, contact:
- Email: support@vidyarthibookdepot.com
- Documentation: https://docs.vidyarthibookdepot.com

