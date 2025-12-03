# Security Features API Documentation

## Authentication & Token Management

### POST /api/auth/refresh

Refresh access token using refresh token with automatic rotation.

**Request:**

```json
{
  "refreshToken": "uuid-string"
}
```

**Response:**

```json
{
  "accessToken": "jwt-token",
  "refreshToken": "new-uuid-string",
  "expiresIn": 900000,
  "user": {
    "id": 1,
    "username": "user@example.com",
    "email": "user@example.com",
    "role": "USER"
  }
}
```

**Features:**

- Automatically rotates refresh token (old token invalidated)
- Returns new access token AND new refresh token
- Old refresh token cannot be reused

---

## Session Management

### GET /api/sessions

Get all active sessions for the current user.

**Headers:**

```
Authorization: Bearer {access_token}
```

**Response:**

```json
[
  {
    "id": 1,
    "createdAt": "2025-12-03T20:00:00Z",
    "expiresAt": "2025-12-10T20:00:00Z"
  }
]
```

### DELETE /api/sessions/{tokenId}

Revoke a specific session.

**Headers:**

```
Authorization: Bearer {access_token}
```

**Path Parameters:**

- `tokenId` - ID of the session to revoke

**Response:**

```
200 OK
```

**Error Responses:**

- `404 Not Found` - Token not found
- `403 Forbidden` - Not authorized to revoke this token

### DELETE /api/sessions/all

Revoke all sessions except the current one.

**Headers:**

```
Authorization: Bearer {access_token}
```

**Response:**

```
200 OK
```

**Use Case:** "Logout from all devices" feature

---

## Admin Endpoints

### GET /api/audit/all

Get all audit logs (admin only).

**Authorization:**

- Requires `ADMIN` role
- Returns `403 Forbidden` for non-admin users

**Headers:**

```
Authorization: Bearer {access_token}
```

**Response:**

```json
[
  {
    "id": 1,
    "userId": 123,
    "action": "LOGIN",
    "details": "User logged in successfully",
    "timestamp": "2025-12-03T20:00:00Z"
  }
]
```

---

## Rate Limiting

**Protected Endpoints:**

- `POST /api/auth/login`
- `POST /api/auth/register`

**Limit:** 5 requests per minute per IP address

**Response when rate limited:**

```json
{
  "message": "Too many requests. Please try again later."
}
```

**Status Code:** `429 Too Many Requests`

**Implementation Details:**

- Tracks requests by IP address
- Considers `X-Forwarded-For` header for proxy environments
- Automatic cleanup of expired rate limit entries
- In-memory storage (resets on server restart)

---

## Password Validation

**Endpoint:** `POST /api/auth/register`

**Password Requirements:**

- Minimum 8 characters
- At least one uppercase letter (A-Z)
- At least one lowercase letter (a-z)
- At least one digit (0-9)
- At least one special character (!@#$%^&*()_+-=[]{}; ':"\\|,.<>/?)

**Example Error Response:**

```json
{
  "message": "Password must contain at least one uppercase letter; Password must contain at least one digit; Password must contain at least one special character"
}
```

**Status Code:** `400 Bad Request`

---

## Security Headers

All responses include the following security headers:

```
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
Content-Security-Policy: default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:;
Strict-Transport-Security: max-age=31536000; includeSubDomains
```

---

## Token Expiration

| Token Type | Expiration |
|------------|------------|
| Access Token | 15 minutes |
| Refresh Token | 7 days |

**Note:** Refresh tokens are automatically rotated on use, extending the session without requiring re-authentication.
