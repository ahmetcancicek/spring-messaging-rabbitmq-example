# Spring Messaging RabbitMQ Example

The repository contains simple a domain driven design project to create an example of using a notification
system with RabbitMQ. At the same time, there are some banking operations that use a low-level locking system.

## Run

To run the application

```
mvn clean install -Dmaven.skip.test=true
docker-compose up -d
```

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
-d '{"securityNo":"56094509238","firstName":"Charles","lastName":"Bootle","dateOfBirth":"1980-03-03"}'
```

```json
{
  "data": {
    "dateOfBirth": "1980-03-03",
    "firstName": "Charles",
    "id": "c9fc855a-d22f-4fea-93a2-044d4e9d294d",
    "lastName": "Bottle",
    "securityNo": "56094509238"
  },
  "errors": null
}
```

### Get Customer

```curl
curl -X GET 'localhost:8090/api/v1/customers/c9fc855a-d22f-4fea-93a2-044d4e9d294d'
```

```json
{
  "data": {
    "dateOfBirth": "1980-03-03",
    "firstName": "Charles",
    "id": "c9fc855a-d22f-4fea-93a2-044d4e9d294d",
    "lastName": "Bottle",
    "securityNo": "56094509238"
  },
  "errors": null
}
```

### Update Customer

```curl
curl -X PUT 'localhost:8090/api/v1/customers/c9fc855a-d22f-4fea-93a2-044d4e9d294d' \
-H 'Content-Type:application/json' \
-d '{"securityNo":"56094509398","firstName":"Charles","lastName":"Bottle","dateOfBirth":"1980-03-03"}'
```

```json
{
  "data": {
    "dateOfBirth": "1980-03-03",
    "firstName": "Charles",
    "id": "c9fc855a-d22f-4fea-93a2-044d4e9d294d",
    "lastName": "Bottle",
    "securityNo": "56094509398"
  },
  "errors": null
}
```

### Create Account

```curl
curl -X POST 'http://localhost:8090/api/v1/accounts' \
-H 'Content-Type: application/json' \
-d '{"customerId":"c9fc855a-d22f-4fea-93a2-044d4e9d294d","name":"My Debin EURO Account","balance":500,"currency":"EUR"}'
```

```json
{
  "data": {
    "balance": 500,
    "currency": "EUR",
    "customerId": "c9fc855a-d22f-4fea-93a2-044d4e9d294d",
    "id": "c30007d9-271b-421a-94b1-0745553b1200",
    "name": "My Debin EURO Account"
  },
  "errors": null
}
```

### Withdraw Money

```curl
curl -X PUT 'http://localhost:8090/api/v1/accounts/withdraw' \
-H 'Content-Type: application/json' \
-d '{"fromId":"c30007d9-271b-421a-94b1-0745553b1200","amount":10}'
```

```json
{
  "data" : {
    "balance" : 490,
    "currency" : "EUR",
    "customerId" : "c9fc855a-d22f-4fea-93a2-044d4e9d294d",
    "id" : "c30007d9-271b-421a-94b1-0745553b1200",
    "name" : "My Debin EURO Account"
  },
  "errors" : null
}
```

### Deposit Money

```curl
curl -X PUT 'http://localhost:8090/api/v1/accounts/deposit' \
-H 'Content-Type: application/json' \
-d '{"toId":"c30007d9-271b-421a-94b1-0745553b1200","amount":10}'
```

```json
{
  "data" : {
    "balance" : 500,
    "currency" : "EUR",
    "customerId" : "c9fc855a-d22f-4fea-93a2-044d4e9d294d",
    "id" : "c30007d9-271b-421a-94b1-0745553b1200",
    "name" : "My Debin EURO Account"
  },
  "errors" : null
}
```

### Transfer Money

```curl
curl -X PUT 'http://localhost:8090/api/v1/accounts/transfer' \
-H 'Content-Type: application/json' \
-d '{"fromId":"c30007d9-271b-421a-94b1-0745553b1200","toId":"4d699286-7022-4a69-833d-03459d24331c","amount":10}'
```

```json
{
   "data" : {
      "amount" : 10,
      "fromAccountId" : "c30007d9-271b-421a-94b1-0745553b1200",
      "fromCustomerId" : "c9fc855a-d22f-4fea-93a2-044d4e9d294d",
      "toAccountId" : "4d699286-7022-4a69-833d-03459d24331c",
      "toCustomerId" : "c27d430c-c335-4f50-b70e-640bd59c18ee"
   },
   "errors" : null
}
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



