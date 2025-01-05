# Backend (Spring Boot API)
The backend is a Spring Boot application that provides APIs for the React frontend.<br/>
Location: `backend/CoinApi`

## How to Build and Run the Spring Boot Application Locally
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

## Using the Dockerfile for the Spring Boot App
The Spring Boot app already contains a Dockerfile in the `backend/CoinApi` directory. You can use it to build and run a Docker container for the Spring Boot application.

### Steps to Build and Run the Container
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