# 2025_NgJiaMin: Coin Calculator

This repository contains two applications:
* Backend (Spring Boot API): Located in the `backend/CoinApi` directory.
* Frontend (React App): Located in the `frontend/coin-calculator` directory.

The React app is bundled with the Spring Boot application and served directly by it in production. 
For development purposes, you can run the React app separately.

---
## Table of Contents
1. [Repository Structure](#repository-structure)
2. [Backend (Spring Boot API)](#backend-spring-boot-api)
   - [Build and Run Locally](#how-to-build-and-run-the-spring-boot-application-locally)
3. [Frontend (React App)](#frontend-react-app)
   - [For Development](#for-development)
   - [For Deployment](#for-deployment)
4. [Using the Dockerfile for the Spring Boot App](#using-the-dockerfile-for-the-spring-boot-app)
   - [Steps to Build and Run the Container](#steps-to-build-and-run-the-container)
5. [Docker Testing Instructions](#docker-testing-instructions)
   - [Testing the API with Postman or cURL](#testing-the-api-with-postman-or-curl)

---
### Repository Structure
```
.
├── backend/CoinApi            # Spring Boot API Application
├── frontend/coin-calculator   # React Application
└── README.md                  # Project documentation
```

## Backend (Spring Boot API)
The backend is a Spring Boot application that provides APIs for the React frontend.<br/>
Location: `backend/CoinApi`

### How to Build and Run the Spring Boot Application Locally
1. Navigate to the `backend/CoinApi` directory:
   ```bash
   cd backend/CoinApi
   ```

2. Build the application using Maven:
   ```bash
   mvn clean package
   ```

3. Run the application:
   ```bash
   java -jar target/CoinApi-0.0.1-SNAPSHOT.jar
   ```

4. The application will start on http://localhost:8080.
---
## Frontend (React App)
The frontend is a React application that communicates with the Spring Boot API.<br/>
Location: `frontend/coin-calculator`

### For Development
If you're actively developing the React app, you can run it separately using the development server.

1. Navigate to the `frontend/coin-calculator` directory:
   ```bash
   cd frontend/coin-calculator
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. (Optional) Set up a proxy for API requests to the Spring Boot back-end:<br/>
   - Open `frontend/package.json` and add:
   ```json
   "proxy": "http://localhost:8080"
   ```

4. Start the React app in development mode:
   ```bash
   npm start
   ```

5. Access the app in your browser at http://localhost:3000.<br/>
API calls (e.g., /api/coins/calculate) will be proxied to the Spring Boot app.

### For Deployment
In production, the React app is bundled with the Spring Boot application and served from the same server.

1. Build the React app:
   ```bash
   npm run build
   ```

2. Copy the `build/` output to the Spring Boot static/ directory:
   ```bash
   cp -r build/* ../backend/src/main/resources/static/
   ```

3. Rebuild the Spring Boot application:
   ```bash 
   mvn clean package
   ```

4. Deploy and start the Spring Boot application. The React app will be served automatically from:
   ```arduino
   http://<CONTAINER_PUBLIC_IP>:8080
   ```

# Using the Dockerfile for the Spring Boot App
The Spring Boot app already contains a Dockerfile in the `backend/CoinApi` directory. You can use it to build and run a Docker container for the Spring Boot application.

## Steps to Build and Run the Container
1. Navigate to the Backend Directory:
   ```bash
   cd backend/CoinApi
   ```
<br/>

2. **Build the Docker Image:** Use the docker build command to create a Docker image for the Spring Boot application:
   * Replace `spring-boot-app:v1` with your desired image name and tag.
   ```bash
   docker build -t spring-boot-app:v1 .
   ```
<br/>

3. **Run the Docker Container:** Start the Docker container using the newly created image:
   ```bash
   docker run -p 8080:8080 spring-boot-app:v1
   ```
<br/>

4. **Access the Application:**
   * Open your browser and go to: http://localhost:8080.
   * The React frontend and Spring Boot API will both be accessible from this address.

<br/>

5. **Stopping the Container:** If you need to stop the running container, find its container ID and stop it:
   ```bash
   docker ps
   docker stop <container_id>
   ```

## Docker Testing Instructions
### Testing the API with Postman or cURL
**Example cURL command:**
```bash
curl -X POST http://localhost:8080/api/coins/calculate \
-H "Content-Type: application/json" \
-d '{
  "targetAmount": 2.35,
  "denominations": [0.1, 0.2, 0.5, 1.0]
}'
```
<br/>

**Example Postman Request:**
1. Create a new `POST` request.

2. Set the URL to:
   ```bash
   http://localhost:8080/api/coins/calculate
   ```

3. Add a `JSON` body, for example:
   ```json
   {
     "targetAmount": 2.35,
     "denominations": [0.1, 0.2, 0.5, 1.0]
   }
   ```

4. Send the request and confirm the API returns the correct response.