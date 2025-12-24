# LearnPath Backend - Spring Boot with Google OAuth2 and MongoDB

## Prerequisites
- Java 17+
- Maven 3.8+
- MongoDB (local or cloud)
- Google Cloud Console account

## Setup MongoDB

### Option 1: Local MongoDB
```bash
# Install MongoDB
brew install mongodb-community  # macOS
# or download from https://www.mongodb.com/try/download/community

# Start MongoDB
mongod --dbpath /data/db
```

### Option 2: MongoDB Atlas (Cloud)
1. Go to [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
2. Create a free cluster
3. Get your connection string
4. Set it as `MONGODB_URI` environment variable

## Setup Google OAuth2

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing
3. Go to **APIs & Services** > **Credentials**
4. Click **Create Credentials** > **OAuth client ID**
5. Select **Web application**
6. Add authorized redirect URIs:
   - `http://localhost:8080/api/auth/oauth2/callback/google`
7. Copy the **Client ID** and **Client Secret**

## Configuration

Set environment variables:

```bash
export MONGODB_URI=mongodb://localhost:27017/learnpath
export GOOGLE_CLIENT_ID=your-google-client-id
export GOOGLE_CLIENT_SECRET=your-google-client-secret
export JWT_SECRET=your-super-secret-jwt-key-at-least-256-bits
```

## Running the Application

```bash
cd backend
mvn spring-boot:run
```

The server will start at `http://localhost:8080`

## API Endpoints

### Authentication
- `GET /api/auth/oauth2/authorize/google` - Initiates Google OAuth flow
- `GET /api/auth/me` - Get current authenticated user
- `POST /api/auth/validate` - Validate JWT token
- `POST /api/auth/logout` - Logout

### Courses (Public)
- `GET /api/courses` - Get all active courses
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/category/{category}` - Get courses by category
- `POST /api/courses` - Create a course
- `PUT /api/courses/{id}` - Update a course
- `DELETE /api/courses/{id}` - Delete a course

## OAuth Flow
1. Frontend redirects to: `http://localhost:8080/api/auth/oauth2/authorize/google`
2. User authenticates with Google
3. Backend redirects to: `http://localhost:5173/auth/callback?token=JWT_TOKEN`
4. Frontend stores token and uses for authenticated requests


curl -X POST http://localhost:8080/api/courses \
 {
"id": "dsa",
"title": "Data Structures & Algorithms",
"description": "Master DSA concepts with hands-on practice",
"gradient": "from-blue-500 to-cyan-500",
"externalUrl": "https://www.geeksforgeeks.org/data-structures/"
}

2.POST- http://localhost:8080/api/auth/login \
{
"email": "s@gmail.com",
"password": "123456"
3.POST- http://localhost:8080/api/auth/validate
pass token(bearer)


**`/api/auth/login`** - Authenticates credentials (email/password), returns a new JWT token if valid.

**`/api/auth/validate`** - Verifies an existing JWT token from the `Authorization` header, returns user info if the token is still valid.

| Endpoint | Purpose | Input | Output |
|----------|---------|-------|--------|
| `/login` | Create new session | `{email, password}` | New JWT + user |
| `/validate` | Check existing token | `Authorization: Bearer ` | User info if valid |

Use `/login` to sign in, `/validate` to check if a stored token is still valid (e.g., on page refresh).

Add token refresh
Add remember me feature
## Sample Course Data
The application automatically seeds sample courses on first startup if the database is empty.
