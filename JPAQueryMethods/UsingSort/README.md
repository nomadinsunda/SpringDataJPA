다음은 `UserController`를 테스트할 수 있는 RESTful API의 엔드포인트 목록과 설명입니다. 각 엔드포인트는 `UserService`와 상호작용하여 `UserController`에서 제공하는 기능을 테스트할 수 있습니다.

### 1. 사용자 생성 (POST 요청)

**Endpoint:**

```
POST /api/users
```

**설명:** 새로운 사용자를 생성합니다.

**Request Body (JSON):**
```json
{
    "firstname": "John",
    "lastname": "Doe",
    "age": 30,
    "active": true
}
```

**Response (JSON):**
```json
{
    "firstname": "John",
    "lastname": "Doe",
    "age": 30,
    "active": true
}
```

---

### 2. 성(lastname)으로 사용자 검색 및 정렬 (GET 요청)

**Endpoint:**

```
GET /api/users/lastname/{lastname}
```

**설명:** 성을 기준으로 사용자를 검색하고, 지정된 필드를 기준으로 정렬합니다.

**Path Parameter:**
- `lastname`: 검색할 성

**Query Parameter:**
- `sortBy`: 정렬할 필드명 (예: `firstname`, `age` 등)

**Example Request:**
```
GET /api/users/lastname/Smith?sortBy=firstname
```

**Response (JSON):**
```json
[
    {
        "firstname": "Alice",
        "lastname": "Smith",
        "age": 25,
        "active": true
    },
    {
        "firstname": "Bob",
        "lastname": "Smith",
        "age": 32,
        "active": false
    }
]
```

---

### 3. 성(lastname)으로 배열 형태로 사용자 검색 및 정렬 (GET 요청)

**Endpoint:**

```
GET /api/users/lastname/array/{lastname}
```

**설명:** 성을 기준으로 사용자를 검색하고, 배열 형태로 반환하며, 지정된 필드를 기준으로 정렬합니다.

**Path Parameter:**
- `lastname`: 검색할 성

**Query Parameter:**
- `sortBy`: 정렬할 필드명 (예: `fn_len`)

**Example Request:**
```
GET /api/users/lastname/array/Smith?sortBy=fn_len
```

**Response (JSON):**
```json
[
    [1, 5],
    [2, 3]
]
```

---

### 4. 이름(firstname) 끝으로 검색 (GET 요청)

**Endpoint:**

```
GET /api/users/firstname/{firstname}
```

**설명:** 이름이 특정 문자열로 끝나는 사용자를 검색합니다.

**Path Parameter:**
- `firstname`: 검색할 이름의 끝부분

**Example Request:**
```
GET /api/users/firstname/son
```

**Response (JSON):**
```json
[
    {
        "firstname": "Jackson",
        "lastname": "Williams",
        "age": 40,
        "active": true
    },
    {
        "firstname": "Emerson",
        "lastname": "Brown",
        "age": 22,
        "active": true
    }
]
```

---

### 5. 전체 사용자 목록 조회 (GET 요청)

**Endpoint:**

```
GET /api/users
```

**설명:** 전체 사용자의 목록을 조회합니다.

**Example Request:**
```
GET /api/users
```

**Response (JSON):**
```json
[
    {
        "firstname": "John",
        "lastname": "Doe",
        "age": 30,
        "active": true
    },
    {
        "firstname": "Jane",
        "lastname": "Doe",
        "age": 25,
        "active": false
    }
]
```

---


### API 테스트 방법

1. **Postman**이나 **curl**을 사용하여 `GET` 요청을 테스트할 수 있습니다.

2. **Example Request**:
    ```
    GET /api/users/search?lastname=Doe&firstname=John
    ```

3. **Example Response (JSON)**:
    ```json
    {
        "firstname": "John",
        "lastname": "Doe",
        "age": 30,
        "active": true
    }
    ```


### Postman 또는 Curl을 통해 API 테스트

1. **Postman**:
   - `POST /api/users`: Body에 JSON 입력하여 사용자 생성.
   - `GET /api/users/lastname/{lastname}`: 쿼리 파라미터 `sortBy`에 정렬할 필드 입력.
   - `GET /api/users/firstname/{firstname}`: 이름 끝으로 검색.

2. **Curl**:
   ```bash
   curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"firstname": "John", "lastname": "Doe", "age": 30, "active": true}'

   curl -X GET http://localhost:8080/api/users/lastname/Smith?sortBy=firstname

   curl -X GET http://localhost:8080/api/users/firstname/son
   ```

이 API들을 통해 `UserController`의 다양한 기능을 테스트할 수 있습니다.