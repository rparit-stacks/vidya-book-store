# API Endpoints - Complete List

## Overview

This document provides a comprehensive list of all API endpoints organized by service. Total: **48 endpoints** across **7 services**.

---

## Authentication Service (3 endpoints)

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| POST | `/api/auth/register` | User registration | No | Public |
| POST | `/api/auth/login` | User login (returns JWT) | No | Public |
| POST | `/api/auth/refresh` | Refresh JWT token | Yes | All |

---

## User Service (5 endpoints)

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| GET | `/api/users/profile` | Get current user profile | Yes | All |
| PUT | `/api/users/profile` | Update user profile | Yes | All |
| GET | `/api/users/{id}` | Get user by ID | Yes | OWNER |
| PUT | `/api/users/{id}/role` | Update user role | Yes | OWNER |
| GET | `/api/users` | List all users | Yes | OWNER |

---

## Product Service (12 endpoints)

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| GET | `/api/products` | List products with filters | No | Public |
| GET | `/api/products/{id}` | Get product details | No | Public |
| GET | `/api/products/search` | Search products | No | Public |
| GET | `/api/products/category/{categoryId}` | Get products by category | No | Public |
| GET | `/api/products/board/{board}` | Get products by board | No | Public |
| GET | `/api/products/class/{class}` | Get products by class | No | Public |
| POST | `/api/products` | Create product | Yes | OWNER |
| PUT | `/api/products/{id}` | Update product | Yes | OWNER |
| DELETE | `/api/products/{id}` | Delete product | Yes | OWNER |
| GET | `/api/products/featured` | Get featured products | No | Public |
| GET | `/api/products/bestsellers` | Get bestseller products | No | Public |
| GET | `/api/products/trending` | Get trending products | No | Public |

---

## Category Service (6 endpoints)

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| GET | `/api/categories` | List all categories | No | Public |
| GET | `/api/categories/{id}` | Get category with subcategories | No | Public |
| GET | `/api/categories/tree` | Get category tree structure | No | Public |
| POST | `/api/categories` | Create category | Yes | OWNER |
| PUT | `/api/categories/{id}` | Update category | Yes | OWNER |
| DELETE | `/api/categories/{id}` | Delete category | Yes | OWNER |

---

## Inquiry Service (10 endpoints)

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| POST | `/api/inquiries` | Create new inquiry | Yes | CUSTOMER |
| GET | `/api/inquiries` | List inquiries | Yes | All |
| GET | `/api/inquiries/{id}` | Get inquiry details | Yes | All* |
| PUT | `/api/inquiries/{id}/status` | Update inquiry status | Yes | OWNER |
| POST | `/api/inquiries/{id}/response` | Add response to inquiry | Yes | OWNER |
| GET | `/api/inquiries/my-inquiries` | Get current user's inquiries | Yes | CUSTOMER |
| GET | `/api/inquiries/pending` | Get pending inquiries | Yes | OWNER |
| GET | `/api/inquiries/statistics` | Get inquiry statistics | Yes | OWNER |
| PUT | `/api/inquiries/{id}` | Update inquiry | Yes | All* |
| DELETE | `/api/inquiries/{id}` | Delete inquiry | Yes | All* |

*CUSTOMER can only access/update their own inquiries

---

## Chat Service (8 endpoints)

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| POST | `/api/chat/conversations` | Create/Get conversation | Yes | CUSTOMER |
| GET | `/api/chat/conversations` | List user's conversations | Yes | All |
| GET | `/api/chat/conversations/{id}` | Get conversation with messages | Yes | All* |
| POST | `/api/chat/conversations/{id}/messages` | Send message | Yes | All* |
| GET | `/api/chat/conversations/{id}/messages` | Get messages (paginated) | Yes | All* |
| PUT | `/api/chat/messages/{id}/read` | Mark message as read | Yes | All |
| GET | `/api/chat/unread-count` | Get unread message count | Yes | All |
| GET | `/api/chat/conversations/owner/all` | Get all conversations | Yes | OWNER |

*Must be participant in conversation

---

## Notification Service (4 endpoints)

| Method | Endpoint | Description | Auth Required | Role |
|--------|----------|-------------|---------------|------|
| GET | `/api/notifications` | Get user notifications | Yes | All |
| PUT | `/api/notifications/{id}/read` | Mark notification as read | Yes | All |
| PUT | `/api/notifications/read-all` | Mark all as read | Yes | All |
| GET | `/api/notifications/unread-count` | Get unread count | Yes | All |

---

## WebSocket Endpoints

| Endpoint | Description | Auth Required |
|----------|-------------|---------------|
| `/ws/chat` | WebSocket connection for real-time chat | Yes |
| `/topic/chat/{conversationId}` | Subscribe to conversation messages | Yes |
| `/app/chat/send` | Send message via WebSocket | Yes |

---

## Endpoint Summary by Service

| Service | Endpoint Count | Public Endpoints | Protected Endpoints |
|----------|---------------|------------------|---------------------|
| Authentication | 3 | 2 | 1 |
| User | 5 | 0 | 5 |
| Product | 12 | 9 | 3 |
| Category | 6 | 3 | 3 |
| Inquiry | 10 | 0 | 10 |
| Chat | 8 | 0 | 8 |
| Notification | 4 | 0 | 4 |
| **Total** | **48** | **14** | **34** |

---

## Endpoint Summary by HTTP Method

| Method | Count | Description |
|--------|-------|-------------|
| GET | 28 | Retrieve resources |
| POST | 11 | Create resources |
| PUT | 8 | Update resources |
| DELETE | 2 | Delete resources |
| **Total** | **48** | |

---

## Public vs Protected Endpoints

### Public Endpoints (14)
- Authentication: Register, Login
- Products: List, Search, Get Details, Filter by Category/Board/Class, Featured/Bestseller/Trending
- Categories: List, Get by ID, Get Tree

### Protected Endpoints (34)
- All User management endpoints
- Product creation/update/delete
- Category management
- All Inquiry endpoints
- All Chat endpoints
- All Notification endpoints

---

## Role-Based Access Summary

### CUSTOMER Role
- ✅ All public endpoints
- ✅ Own profile management
- ✅ Create and manage own inquiries
- ✅ Chat with owner
- ✅ View own notifications
- ❌ Product/Category management
- ❌ User management
- ❌ Inquiry responses

### OWNER Role
- ✅ All CUSTOMER permissions
- ✅ Product CRUD operations
- ✅ Category CRUD operations
- ✅ Respond to inquiries
- ✅ View all inquiries
- ✅ View all conversations
- ✅ User management
- ✅ Inquiry statistics

---

## Query Parameters Reference

### Common Query Parameters

| Parameter | Type | Description | Used In |
|-----------|------|-------------|---------|
| `page` | Integer | Page number (0-indexed) | All list endpoints |
| `size` | Integer | Page size | All list endpoints |
| `sort` | String | Sort field | Product list |
| `direction` | String | Sort direction (ASC/DESC) | Product list |

### Product-Specific Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| `categoryId` | Long | Filter by category |
| `board` | String | Filter by board (SSC/HSC/ICSE/CBSE) |
| `class` | String | Filter by class/grade |
| `search` | String | Search query |
| `featured` | Boolean | Filter featured products |
| `bestseller` | Boolean | Filter bestseller products |
| `limit` | Integer | Limit results (for featured/bestseller/trending) |
| `period` | String | Time period (WEEK/MONTH/YEAR) |

### Inquiry-Specific Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| `status` | String | Filter by status (PENDING/IN_PROGRESS/RESOLVED/CLOSED) |
| `type` | String | Filter by type (BOOK_AVAILABILITY/GENERAL_QUESTION/CUSTOM_ORDER) |
| `productId` | Long | Filter by product |

### Notification-Specific Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| `read` | Boolean | Filter by read status |
| `type` | String | Filter by notification type |

### Chat-Specific Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| `unreadOnly` | Boolean | Filter only unread conversations |

---

## Response Status Codes

| Status Code | Meaning | Common Endpoints |
|-------------|---------|------------------|
| 200 OK | Successful GET/PUT request | Most GET/PUT endpoints |
| 201 Created | Resource created successfully | POST endpoints |
| 204 No Content | Successful deletion | DELETE endpoints |
| 400 Bad Request | Invalid request data | All endpoints |
| 401 Unauthorized | Authentication required | Protected endpoints |
| 403 Forbidden | Insufficient permissions | Role-restricted endpoints |
| 404 Not Found | Resource not found | GET/PUT/DELETE by ID |
| 409 Conflict | Resource conflict | Duplicate creation |

---

## Base URLs

### Development
```
http://localhost:8080/api
```

### Production
```
https://api.vidyarthibookdepot.com/api
```

### WebSocket (Development)
```
ws://localhost:8080/ws/chat
```

### WebSocket (Production)
```
wss://api.vidyarthibookdepot.com/ws/chat
```

---

## Quick Reference by Use Case

### Customer Use Cases

**Browse Products**
- `GET /api/products` - Browse all products
- `GET /api/products/search?q={query}` - Search products
- `GET /api/products/category/{id}` - Browse by category
- `GET /api/products/board/{board}` - Browse by board
- `GET /api/products/{id}` - View product details

**Manage Account**
- `POST /api/auth/register` - Create account
- `POST /api/auth/login` - Login
- `GET /api/users/profile` - View profile
- `PUT /api/users/profile` - Update profile

**Make Inquiries**
- `POST /api/inquiries` - Create inquiry
- `GET /api/inquiries/my-inquiries` - View my inquiries
- `GET /api/inquiries/{id}` - View inquiry details

**Chat with Owner**
- `POST /api/chat/conversations` - Start conversation
- `GET /api/chat/conversations` - View conversations
- `POST /api/chat/conversations/{id}/messages` - Send message
- `GET /api/chat/conversations/{id}/messages` - View messages

**View Notifications**
- `GET /api/notifications` - View notifications
- `GET /api/notifications/unread-count` - Unread count
- `PUT /api/notifications/{id}/read` - Mark as read

### Owner Use Cases

**Manage Products**
- `POST /api/products` - Add product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products` - View all products

**Manage Categories**
- `POST /api/categories` - Add category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category
- `GET /api/categories/tree` - View category tree

**Handle Inquiries**
- `GET /api/inquiries/pending` - View pending inquiries
- `GET /api/inquiries/{id}` - View inquiry details
- `POST /api/inquiries/{id}/response` - Respond to inquiry
- `PUT /api/inquiries/{id}/status` - Update status
- `GET /api/inquiries/statistics` - View statistics

**Manage Users**
- `GET /api/users` - View all users
- `GET /api/users/{id}` - View user details
- `PUT /api/users/{id}/role` - Update user role

**Chat Management**
- `GET /api/chat/conversations/owner/all` - View all conversations
- `GET /api/chat/conversations/{id}` - View conversation
- `POST /api/chat/conversations/{id}/messages` - Send message

---

## Notes

1. **Authentication**: Most endpoints require JWT token in `Authorization: Bearer {token}` header
2. **Pagination**: All list endpoints support pagination with `page` and `size` parameters
3. **Filtering**: Most list endpoints support filtering via query parameters
4. **Role-Based Access**: Some endpoints are restricted to specific roles (OWNER, CUSTOMER)
5. **WebSocket**: Real-time chat uses WebSocket connections in addition to REST APIs
6. **Error Handling**: All endpoints return consistent error response format
7. **Versioning**: Currently unversioned; future versions will use `/api/v1/`, `/api/v2/`, etc.

---

## Related Documentation

- **API Documentation**: See `api-documentation.md` for detailed endpoint specifications
- **Service Documentation**: See individual service docs (`user-service.md`, `product-service.md`, etc.)
- **Architecture**: See `main-architecture.md` for system overview
- **Deployment**: See `deployment-guide.md` for deployment instructions

