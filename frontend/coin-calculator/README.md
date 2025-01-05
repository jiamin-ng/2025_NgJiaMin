# Frontend (React App)
The frontend is a React application that communicates with the Spring Boot API.<br/>
Location: `frontend/coin-calculator`

## For Development
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

## For Deployment
In production, the React app is bundled with the Spring Boot application and served from the same server.

1. Build the React app:
   ```bash
   npm run build
   ```

2. Copy the `build/` output to the Spring Boot `static/ directory`:
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