# This is a sample Springboot project for a products entity api server with oauth client credentials. 

## Fetch Token

Sample curl command:

```shell
curl --location 'localhost:8080/oauth/token' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--form 'grant_type="client_credentials"'
```

Sample response:

```json
{
    "access_token": "2c579673-7743-4d5c-a6e2-0929d4594830",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read write"
}
```

## Product APIs

#### Fill in {access_token} from the one retrieved above

### 1. Fetch page of products

Sample curl command:

```shell
curl --location 'http://localhost:8080/api/v1/products?page=0&size=10' \
--header 'Authorization: Bearer {access_token}'
```

Sample response:

```json
{
    "content": [
        {
            "id": 1,
            "name": "Name 1",
            "description": "Description 1",
            "type": "food",
            "quantity": 1,
            "price": 1.1
        },
        {
            "id": 2,
            "name": "Name 2",
            "description": "Description 2",
            "type": "sports",
            "quantity": 2,
            "price": 2.2
        },
        {
            "id": 3,
            "name": "Name 3",
            "description": "Description 3",
            "type": "sports",
            "quantity": 3,
            "price": 3.3
        }
    ],
    "pageable": {
        "sort": {
            "unsorted": true,
            "sorted": false,
            "empty": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 10,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 3,
    "size": 10,
    "sort": {
        "unsorted": true,
        "sorted": false,
        "empty": true
    },
    "number": 0,
    "first": true,
    "numberOfElements": 3,
    "empty": false
}
```

### 2. Fetch specific product

Sample curl command:

```shell
curl --location 'http://localhost:8080/api/v1/products/1' \
--header 'Authorization: Bearer {access_token}'
```

Sample response:

```json
{
    "id": 1,
    "name": "Name 1",
    "description": "Description 1",
    "type": "food",
    "quantity": 1,
    "price": 1.1
}
```

### 3. Create product

Sample curl command:

```shell
curl --location 'http://localhost:8080/api/v1/products' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {access_token}' \
--data '{
    "name": "Name 4",
    "description": "Description 4",
    "type": "music",
    "quantity": 4,
    "price": 4.4
}'
```

Sample response:

```json
{
    "id": 4,
    "name": "Name 4",
    "description": "Description 4",
    "type": "music",
    "quantity": 4,
    "price": 4.4
}
```

### 4. Update product

Sample curl command:

```shell
curl --location --request PUT 'http://localhost:8080/api/v1/products/4' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer {access_token}' \
--data '{
    "name": "Updated Name 4",
    "description": "Updated Description 4",
    "type": "music",
    "quantity": 5,
    "price": 4.5
}'
```

Sample response:

```json
{
    "id": 4,
    "name": "Updated Name 4",
    "description": "Updated Description 4",
    "type": "music",
    "quantity": 5,
    "price": 4.5
}
```

### 5. Delete product

Sample curl:

```shell
curl --location --request DELETE 'http://localhost:8080/api/v1/products/4' \
--header 'Authorization: Bearer {access_token}'
```

Empty response