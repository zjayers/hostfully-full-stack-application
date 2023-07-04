```text
 _     _                  ___       _ _       
| |   | |           _    / __)     | | |      
| |__ | | ___   ___| |_ | |__ _   _| | |_   _ 
|  __)| |/ _ \ /___)  _)|  __) | | | | | | | |
| |   | | |_| |___ | |__| |  | |_| | | | |_| |
|_|   |_|\___/(___/ \___)_|   \____|_|_|\__  |
                                       (____/ 
```

# Running the Spring Application with React Frontend

This project is a Spring application with a React frontend that communicates with its own API. It utilizes an H2 database for data storage.

## Prerequisites

Make sure you have the following prerequisites installed on your system:

- Java Development Kit (JDK) 11 or higher
- Node.js

## Getting Started

1. Clone the repository or download the project files.

2. Open the project in your preferred IDE or code editor.

3. Configure the H2 Database:
   - Open the `src/main/resources/application.properties` file.
   - Verify that the H2 database configuration is as follows:
     ```
     spring.h2.console.enabled=true
     spring.h2.console.path=/h2-console
     spring.datasource.url=jdbc:h2:mem:testdb
     spring.datasource.driverClassName=org.h2.Driver
     spring.datasource.username=sa
     spring.datasource.password=
     spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
     spring.jpa.hibernate.ddl-auto=create
     spring.jackson.date-format=yyyy/dd/MM
     ```
   - The above configuration sets up an in-memory H2 database with default settings.

## Running in Development Mode

   - Open a terminal and navigate to the project's root directory.
   - Use the following command to run the Spring application with the React frontend in development mode:
     ```
     ./mvnw spring-boot:run -Pdev
     ```
   - This command will start the Spring application and automatically serve the React frontend from the built static files.
   - The Spring application will listen on `http://localhost:8080` and the React app will be accessible at that URL.

## Running in Production Mode

1. Build the Spring Application:
   - Open a terminal and navigate to the project's root directory.
   - Run the following command to build the Spring application:
     ```
     ./mvnw clean package
     ```
   - This command will compile the Java code and package the application into a JAR file.



2. Run the Spring Application in Production Mode:
   - Use the following command to run the packaged JAR file:
     ```
     java -jar target/hostfully-web-application-0.0.1-SNAPSHOT.jar
     ```
   - The Spring application will start and serve the React frontend from the built static files.
   - The application will listen on `http://localhost:8080` and the React app will be accessible at that URL.



3. Access the Application:
   - Open your web browser and go to `http://localhost:8080`.
   - The React frontend should be served from the Spring app's static folders and displayed at the given URL.
   - The React app communicates with its own API on the same host and port (`http://localhost:8080/api`).



4. (Optional) Accessing the H2 Database Console:
   - To access the H2 database console, go to `http://localhost:8080/h2-console`.
   - Make sure the database URL matches the configuration in `application.properties` (`jdbc:h2:mem:testdb`).
   - The username should be `sa`, and no password is required.
   - Click "Connect" to access the H2 database console.

---
