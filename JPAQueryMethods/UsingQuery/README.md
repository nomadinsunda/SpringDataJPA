다음은 `UserController`의 각 핸들러 메서드를 테스트할 수 있는 RESTful API URL 정리입니다.

### 1. **사용자 생성 (Create User)**
- **URL**: `/api/users`
- **HTTP Method**: `POST`
- **Request Body**: `UserDTO` JSON 객체
- **Response**: 생성된 사용자 정보를 반환 (201 Created)

#### 예시
```bash
POST /api/users
Content-Type: application/json

{
    "firstname": "John",
    "lastname": "Doe",
    "age": 30,
    "active": true
}
```

### 2. **모든 사용자 조회 (Get All Users)**
- **URL**: `/api/users`
- **HTTP Method**: `GET`
- **Response**: 모든 사용자 리스트 (200 OK)

#### 예시
```bash
GET /api/users
```

### 3. **특정 사용자 조회 (Get User by ID)**
- **URL**: `/api/users/{id}`
- **HTTP Method**: `GET`
- **Path Variable**: `id` (사용자의 ID)
- **Response**: 사용자 정보 (200 OK), 없으면 404 Not Found

#### 예시
```bash
GET /api/users/1
```

### 4. **사용자 업데이트 (Update User)**
- **URL**: `/api/users/{id}`
- **HTTP Method**: `PUT`
- **Path Variable**: `id` (사용자의 ID)
- **Request Body**: 업데이트할 `UserDTO` JSON 객체
- **Response**: 업데이트된 사용자 정보 (200 OK), 없으면 404 Not Found

#### 예시
```bash
PUT /api/users/1
Content-Type: application/json

{
    "firstname": "Jane",
    "lastname": "Doe",
    "age": 32,
    "active": false
}
```

### 5. **사용자 삭제 (Delete User)**
- **URL**: `/api/users/{id}`
- **HTTP Method**: `DELETE`
- **Path Variable**: `id` (사용자의 ID)
- **Response**: 성공 시 204 No Content

#### 예시
```bash
DELETE /api/users/1
```

### 6. **성으로 사용자 조회 (Get Users by Lastname)**
- **URL**: `/api/users/lastname/{lastname}`
- **HTTP Method**: `GET`
- **Path Variable**: `lastname` (사용자의 성)
- **Response**: 해당 성을 가진 사용자 리스트 (200 OK)

#### 예시
```bash
GET /api/users/lastname/Doe
```

### 7. **활성 사용자 조회 (Get Active Users)**
- **URL**: `/api/users/active`
- **HTTP Method**: `GET`
- **Response**: 활성화된 사용자 리스트 (200 OK)

#### 예시
```bash
GET /api/users/active
```

### 8. **나이 범위로 사용자 조회 (Get Users by Age Range)**
- **URL**: `/api/users/age-range`
- **HTTP Method**: `GET`
- **Query Parameters**: 
  - `minAge`: 최소 나이
  - `maxAge`: 최대 나이
- **Response**: 해당 나이 범위의 사용자 리스트 (200 OK)

#### 예시
```bash
GET /api/users/age-range?minAge=20&maxAge=30
```

### 9. **이름과 성으로 사용자 검색 (Get User by Firstname and Lastname)**
- **URL**: `/api/users/search`
- **HTTP Method**: `GET`
- **Query Parameters**: 
  - `firstname`: 사용자의 이름
  - `lastname`: 사용자의 성
- **Response**: 해당하는 사용자 정보 (200 OK), 없으면 404 Not Found

#### 예시
```bash
GET /api/users/search?firstname=John&lastname=Doe
```

### 10. **이름 끝이 특정 문자열로 끝나는 사용자 조회**
  - **URL**: `/api/users/firstname-endswith`
  - **HTTP Method**: `GET`
  - **Query Parameter**: `firstname`
  - **Response**: 해당 이름으로 끝나는 사용자 리스트 (200 OK)

#### 예시
```bash
GET /api/users/firstname-endswith?firstname=n
```

이렇게 각 핸들러 메서드는 HTTP 메서드와 URL에 따라 다른 작업을 수행하며, 위의 예시를 사용해 Postman, cURL 등을 통해 테스트할 수 있습니다.