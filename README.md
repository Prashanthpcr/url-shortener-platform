QuickLink — A Full-Stack URL Shortening Service
A production-grade, full-stack URL shortening application with a React frontend and a containerized Java Spring Boot backend. Deployed on Render with an automated CI/CD pipeline.

🚀 Live Demo
You can try the live app here:
https://url-shortener-frontend-fmej.onrender.com

Note: Free Render services may "spin down" after inactivity — the first load can be slow.

(Action: replace the screenshot below with a fresh image of your app — see "Screenshots" section.)


✨ Key Features
Interactive & Responsive Frontend — React UI for single and bulk URL shortening with a dynamic results display.

High-Throughput Batch Processing — /api/v1/shorten-batch endpoint supports hundreds of URLs per request and returns detailed 207 Multi-Status responses per URL.

High-Performance Caching — Redis caches hot short-URL lookups for near-instant redirection and lower DB load.

Automated Cloud Deployment — Git push to main triggers Render auto-deploys for frontend and backend with zero downtime (CI/CD).

🏗️ Architecture & Technology Stack
Architecture: decoupled multi-service (frontend, backend, DB, cache).

mermaid
Copy
Edit
graph TD
    subgraph User
        A[Browser]
    end

    subgraph "Render Cloud (Singapore)"
        B[React Frontend on Static Site] -- API Calls --> C{Java Backend on Web Service}
        C -- Reads/Writes --> D[PostgreSQL DB]
        C -- Caches --> E[Redis Cache]
    end

    subgraph GitHub
        F[git push] --> G{Render Auto-Deploy}
        G -- Deploys --> B
        G -- Deploys --> C
    end

    A --> B
Technologies

Frontend: React.js, JavaScript (ES6+), HTML5, CSS3, Axios

Backend: Java 17, Spring Boot 3, Spring Web, Spring Data JPA, Spring Cache

Database & Cache: PostgreSQL, Redis

DevOps & Cloud: Docker, Docker Compose, Nginx, Render (PaaS), CI/CD

Tools: REST, Postman, VS Code, Git & GitHub, Yarn

⚙️ Local Development
Prerequisites
Docker Desktop (or Docker + docker-compose)

Git

(Optional) Node.js & Yarn if you want to run frontend locally without Docker

Clone
bash
Copy
Edit
git clone https://github.com/Prashanthpcr/url-shortener-platform.git
cd url-shortener-platform
Run full stack (Docker)
This docker-compose.yml builds and starts frontend, backend, PostgreSQL and Redis:

bash
Copy
Edit
docker-compose up --build
Frontend: http://localhost:3000

Backend API: http://localhost:8080

Run services individually (optional)
Frontend (local, without Docker)

bash
Copy
Edit
cd frontend
yarn install
yarn start
Backend (local, IDE)

Import backend as a Maven/Gradle project

Ensure application.yml/application.properties points to local Postgres & Redis

Run as a Spring Boot app (Java 17)

⚠️ Environment Variables / Configuration
These are typical values — adjust per your environment / Render service settings.

Backend (application.yml or environment vars)

SPRING_DATASOURCE_URL=jdbc:postgresql://<DB_HOST>:5432/<DB_NAME>

SPRING_DATASOURCE_USERNAME=<db_user>

SPRING_DATASOURCE_PASSWORD=<db_pass>

SPRING_REDIS_HOST=<redis_host>

SPRING_REDIS_PORT=<redis_port>

APP_BASE_URL=https://your-production-domain.com (used for generating full short URLs)

Frontend (example .env)

ini
Copy
Edit
VITE_API_BASE_URL=https://your-backend.onrender.com/api/v1
🔌 API Overview (common endpoints)
Replace with full, up-to-date API docs for your repo.

POST /api/v1/shorten — Create a single short URL.
Body

json
Copy
Edit
{ "url": "https://example.com", "customAlias": "optional" }
POST /api/v1/shorten-batch — Batch shorten URLs (accepts array). Returns 207 Multi-Status with per-item results.

GET /{alias} — Redirects to target URL (HTTP 302 or 301 depending on config).

GET /api/v1/links/{alias} — Get metadata / analytics for a short link.

(Document authentication, rate limits, analytics endpoints, etc. here as needed.)

🧪 Testing
Use Postman or curl to test API endpoints.

Add unit tests for backend services (Spring Boot + JUnit).

Frontend: run unit & integration tests with your chosen test runner (Jest, RTL, etc.).

♻️ Deployment & CI/CD
The repository is configured to auto-deploy to Render when commits are pushed to main.

Each service (frontend / backend) is a separate Render service with its own build settings.

Builds are zero-downtime; health checks and readiness probes are recommended for reliable rollouts.

(If you use GitHub Actions, add the workflow file and brief description here.)

📸 Screenshots
Replace ./docs/screenshot.png with an updated screenshot of your app. To update the README display:

Save a new screenshot to docs/screenshot.png (or update the path in this README).

Commit & push to GitHub — the README will show the new image.

🧾 Troubleshooting & Tips
If Render’s free tier “spins down” your service, the first request after idle may be slow. Consider a keep-alive or upgrade plan for production SLAs.

For local DB connection issues, verify Docker network and that Postgres container has finished initialization.

Check Redis cache keys with redis-cli if short URLs don’t return cached entries.

✍️ Author
Prashanth Reddy
GitHub: @Prashanthpcr
LinkedIn: linkedin.com/in/your-profile (Action: replace with your actual LinkedIn URL.)

