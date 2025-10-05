# Simple Shopping List API
A Simple Spring Boot REST API to manage a shopping list. Uses an in-memory H2 database and OpenAPI/Swagger for docs.

## Run with Maven Wrapper
Requires Java 21 (JDK 21)

- Linux/macOS:
```
chmod +x mvnw
./mvnw spring-boot:run
```

- Windows (cmd.exe):
```
mvnw.cmd spring-boot:run
```

- Windows (PowerShell):
```powershell
.\mvnw.cmd spring-boot:run
```

## Run with Docker (alternative)
Requires Docker.

```bash
docker run --rm -p 8080:8080 "$(docker build -q .)"
```

## Try it
- API: http://localhost:8080/api/v1/items
- Swagger UI: http://localhost:8080/swagger-ui
- H2 Console: http://localhost:8080/h2-console

## API (base path: /api/v1/items)
- GET all
```
curl -X GET "http://localhost:8080/api/v1/items" -H "accept: application/json"
```
- GET by id
```
curl -X GET "http://localhost:8080/api/v1/items/1" -H "accept: application/json"
```
- POST create
```
curl -X POST "http://localhost:8080/api/v1/items" \
  -H "accept: application/json" \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Milk\",\"price\":2.49,\"quantity\":2,\"category\":\"Dairy\"}"
```
- PUT update
```
curl -X PUT "http://localhost:8080/api/v1/items/1" \
  -H "accept: application/json" \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Bread\",\"price\":1.99,\"quantity\":3,\"category\":\"Bakery\"}"
```
- DELETE by id
```
curl -X DELETE "http://localhost:8080/api/v1/items/1" -H "accept: */*"
```
Response fields include computed `cost` (price * quantity) and timestamps.

## H2 Console
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:shoppinglistdb`
- Username: `sa`  Password: (empty)

## Sample data
On startup, items are loaded from `src/main/resources/data.json` into the in-memory DB.

## Test
- Windows:
```
mvnw.cmd test
```
- Linux/macOS:
```
./mvnw test
```
