# Start with a base image that includes Java
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy SpringBoot JAR file into container
COPY target/CoinApi-0.0.1-SNAPSHOT.jar app.jar

# Copy React build files to be served by SpringBoot
COPY src/main/resources/static/ /app/static/

# Expose SpringBoot app port
EXPOSE 8080

# Define command to run SpringBoot app
ENTRYPOINT ["java", "-jar", "app.jar"]
