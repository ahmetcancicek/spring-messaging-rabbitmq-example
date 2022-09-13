# Spring Messaging RabbitMQ Example

The repository contains simple a modular-monolith development project to create example of using notification system
with RabbitMQ. At the same time, there are some banking operation that use low-level locking system.

## Development

To test the application

```bash
mvn test
```

To build and run the application

```bash
docker-compose -f docker-compose.dev.yml up --build -d
mvn spring-boot:run -Dmaven.skip.test=true
```

## API Documentation

### Create Customer

```curl
curl -X POST 'http://localhost:8090/api/v1/customers' \
-H 'Content-Type: application/json' \
-d '{"securityNo":"df7426ef-b754-4495-96d7-02d7e62a68c4","firstName":"Charles","lastName":"Bootle","dateOfBirth":"1980-03-03"}'
```

```json
{
  "data": {
    "id": "25313555-2d85-4d29-94ef-6a2e00e3a97a",
    "securityNo": "df7426ef-b754-4495-96d7-02d7e62a68c4",
    "firstName": "Charles",
    "lastName": "Bootle",
    "dateOfBirth": "1980-03-03"
  },
  "errors": null
}
```

### Get Customer

```curl
curl -X GET 'localhost:8090/api/v1/customers/25313555-2d85-4d29-94ef-6a2e00e3a97a'
```

```json
{
  "data":
  {
    "id":"25313555-2d85-4d29-94ef-6a2e00e3a97a",
    "securityNo":"df7426ef-b754-4495-96d7-02d7e62a68c4",
    "firstName":"Charles",
    "lastName":"Bootle",
    "dateOfBirth":"1980-03-03"
  }, 
  "errors":null
}
```

### Create Account

```curl
curl -X POST 'http://localhost:8090/api/v1/accounts' \
-H 'Content-Type: application/json' \
-d '{"customerId":"0201d74a-0c5b-4b44-8678-9e525bedd4ea","securityNo":"3ca602fa-6683-4365-8a34-f662da25d740","name":"My EUR Account","balance":200,"currency":"EUR"}'
```

```json
{
  "data": {
    "id": "c4d1693e-e6f5-4a3f-b6fb-0a6626de04b0",
    "customerId": "0201d74a-0c5b-4b44-8678-9e525bedd4ea",
    "name": "My EUR Account",
    "balance": 200,
    "currency": "EUR"
  },
  "errors": null
}
```

### Withdraw Money

```curl
curl -X PUT 'http://localhost:8090/api/v1/accounts/withdraw' \
-H 'Content-Type: application/json' \
-d '{"fromId":"c4d1693e-e6f5-4a3f-b6fb-0a6626de04b0","amount":10}'
```

```json
{
  "data": {
    "id": "c4d1693e-e6f5-4a3f-b6fb-0a6626de04b0",
    "customerId": "0201d74a-0c5b-4b44-8678-9e525bedd4ea",
    "name": "My EUR Account",
    "balance": 180.00,
    "currency": "EUR"
  },
  "errors": null
}
```

### Deposit Money

```curl
curl -X PUT 'http://localhost:8090/api/v1/accounts/deposit' \
-H 'Content-Type: application/json' \
-d '{"toId":"c4d1693e-e6f5-4a3f-b6fb-0a6626de04b0","amount":10}'
```

```json
{
  "data": {
    "id": "c4d1693e-e6f5-4a3f-b6fb-0a6626de04b0",
    "customerId": "0201d74a-0c5b-4b44-8678-9e525bedd4ea",
    "name": "My EUR Account",
    "balance": 190.00,
    "currency": "EUR"
  },
  "errors": null
}
```

### Transfer Money

```curl
curl -X PUT 'http://localhost:8090/api/v1/accounts/transfer' \
-H 'Content-Type: application/json' \
-d '{"fromId":"c4d1693e-e6f5-4a3f-b6fb-0a6626de04b0","toId":"4d699286-7022-4a69-833d-03459d24331c","amount":10}'
```

```json

```

## Technologies Used

1. Spring Boot
2. Spring Data
3. Hibernate
4. Java 17
5. MySQL
6. Docker
7. Maven 3

## License

Distributed under the GNU License. See LICENSE.md for more information.

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)



