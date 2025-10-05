# Simple Shopping List API
A tiny Spring Boot (3.x) REST API to manage a shopping list. Uses an in-memory H2 database and OpenAPI/Swagger for docs.

## Requirements
- Java 21 (JDK 21)

## Run
- Linux/macOS:
```
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


App starts on http://localhost:8080

Docs: http://localhost:8080/swagger-ui

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
