# FurnitureStore

FurnitureStore is a Spring Boot MVC application for managing products in a furniture store.
It supports CRUD operations, soft deletion, and uses an H2 database for persistence.

## Features

- Product management (create, read, update, soft delete)
- Soft delete (products are marked as deleted, not removed)
- H2 database for local development
- Spring MVC architecture
- Simple authentication for H2 Console
- Automatic product import on startup (optional)

## Technologies

- Java 17+
- Spring Boot
- Spring MVC
- Spring Data JPA
- H2 Database
- JUnit & Mockito (testing)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven or Gradle

### Running the Application

1. **Clone the repository**
    ```bash
    git clone https://github.com/yourusername/furniturestore.git
    cd furniturestore
    ```

2. **Build and run**
    ```bash
    ./mvnw spring-boot:run
    ```
   or
    ```bash
    ./gradlew bootRun
    ```

3. **Access the application**
    - Main app: [http://localhost:8080](http://localhost:8080)
    - H2 Console: [H2 Console](http://localhost:8080/h2-console)
        - **JDBC URL:** `jdbc:h2:file:./data/furnituredb;AUTO_SERVER=TRUE`
        - **Username:** `sa`
        - **Password:** *(leave blank)*
    - Swagger anotations: [Swagger docs](http://localhost:8080/swagger-ui/index.html)

### Configuration

The application is configured via `application.properties`:

```properties
spring.application.name=FurnitureStore
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.url=jdbc:h2:file:./data/furnituredb;AUTO_SERVER=TRUE
app.import-products-on-startup=true
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=false
spring.security.user.name=user
spring.security.user.password=1234
