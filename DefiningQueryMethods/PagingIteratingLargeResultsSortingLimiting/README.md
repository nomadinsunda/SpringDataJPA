# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#using.devtools)
* [Validation](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#io.validation)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

<br>
아래는 `User` 엔티티와 관련된 RESTful API를 정리한 내용입니다. 각 API 엔드포인트는 HTTP 메서드, URL, 요청 및 응답 형식과 설명을 포함합니다.

### RESTful API 정리

#### 1. 사용자 목록 조회
- **HTTP Method**: `GET`
- **URL**: `/api/users`
- **설명**: 모든 사용자 정보를 조회합니다.
- **응답**: 
  - **200 OK**: 사용자 목록 (List<UserDTO>)

#### 2. 특정 사용자 조회
- **HTTP Method**: `GET`
- **URL**: `/api/users/{id}`
- **설명**: 특정 사용자 ID에 대한 정보를 조회합니다.
- **요청**: 
  - **Path Variable**: `id` (Long) - 조회할 사용자 ID
- **응답**: 
  - **200 OK**: 사용자 정보 (UserDTO)
  - **404 Not Found**: 사용자가 존재하지 않을 경우

#### 3. 사용자 생성
- **HTTP Method**: `POST`
- **URL**: `/api/users`
- **설명**: 새로운 사용자를 생성합니다.
- **요청**: 
  - **Body**: UserDTO JSON 형식
  - 예시:
    ```json
    {
        "firstname": "John",
        "lastname": "Doe",
        "age": 30,
        "active": true
    }
    ```
- **응답**: 
  - **201 Created**: 생성된 사용자 정보 (UserDTO)

#### 4. lastname 기준으로 첫 10명의 사용자 조회 (페이지)
- **HTTP Method**: `GET`
- **URL**: `/api/users/lastname/queryFirst10`
- **설명**: lastname을 기준으로 첫 10명의 사용자 정보를 페이지 형태로 조회합니다.
- **요청**: 
  - **Query Parameter**: `lastname` (String) - 조회할 성
  - **Pageable**: 페이지 정보 (size, page 등)
- **응답**: 
  - **200 OK**: 사용자 목록 (Page<UserDTO>)

#### 5. lastname 기준으로 상위 3명의 사용자 조회 (Slice)
- **HTTP Method**: `GET`
- **URL**: `/api/users/lastname/findTop3`
- **설명**: lastname을 기준으로 상위 3명의 사용자 정보를 Slice 형태로 조회합니다.
- **요청**: 
  - **Query Parameter**: `lastname` (String) - 조회할 성
  - **Pageable**: 페이지 정보 (size, page 등)
- **응답**: 
  - **200 OK**: 사용자 목록 (Slice<UserDTO>)

#### 6. lastname 기준으로 첫 10명의 사용자 조회 (정렬)
- **HTTP Method**: `GET`
- **URL**: `/api/users/lastname/findFirst10`
- **설명**: lastname을 기준으로 첫 10명의 사용자 정보를 정렬하여 조회합니다.
- **요청**: 
  - **Query Parameter**: `lastname` (String) - 조회할 성
  - **Sort Parameter**: 정렬 기준 (예: `sort=age,desc`)
- **응답**: 
  - **200 OK**: 사용자 목록 (List<UserDTO>)

#### 7. lastname 기준으로 상위 10명의 사용자 조회 (페이지)
- **HTTP Method**: `GET`
- **URL**: `/api/users/lastname/findTop10`
- **설명**: lastname을 기준으로 상위 10명의 사용자 정보를 페이지 형태로 조회합니다.
- **요청**: 
  - **Query Parameter**: `lastname` (String) - 조회할 성
  - **Pageable**: 페이지 정보 (size, page 등)
- **응답**: 
  - **200 OK**: 사용자 목록 (List<UserDTO>)

### 예제 요청 및 응답

#### 1. 사용자 목록 조회 예제

- **요청**: 
  ```http
  GET /api/users
  ```

- **응답**:
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
          "lastname": "Smith",
          "age": 25,
          "active": false
      }
  ]
  ```

#### 2. 특정 사용자 조회 예제

- **요청**: 
  ```http
  GET /api/users/1
  ```

- **응답**:
  ```json
  {
      "firstname": "John",
      "lastname": "Doe",
      "age": 30,
      "active": true
  }
  ```

#### 3. 사용자 생성 예제

- **요청**: 
  ```http
  POST /api/users
  Content-Type: application/json

  {
      "firstname": "Alice",
      "lastname": "Johnson",
      "age": 28,
      "active": true
  }
  ```

- **응답**:
  ```json
  {
      "firstname": "Alice",
      "lastname": "Johnson",
      "age": 28,
      "active": true
  }
  ```


