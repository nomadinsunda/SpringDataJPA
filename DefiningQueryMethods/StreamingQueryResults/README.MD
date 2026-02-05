
### URL로 테스트하기

#### HTTP 클라이언트 도구

**Postman**이나 **cURL**과 같은 HTTP 클라이언트 도구를 사용하여 API를 테스트할 수 있습니다. 

#### API 테스트

1. **모든 사용자 조회**

   ```http
   GET http://localhost:8080/users
   ```

2. **사용자 생성**

   ```http
   POST http://localhost:8080/users
   Content-Type: application/json

   {
       "firstname": "NewFirstName",
       "lastname": "NewLastName"
   }
   ```

3. **특정 사용자 조회**

   ```http
   GET http://localhost:8080/users/{id}
   ```

   여기서 `{id}`는 조회할 사용자의 ID입니다.

4. **사용자 업데이트**

   ```http
   PUT http://localhost:8080/users/{id}
   Content-Type: application/json

   {
       "firstname": "UpdatedFirstName",
       "lastname": "UpdatedLastName"
   }
   ```

5. **사용자 삭제**

   ```http
   DELETE http://localhost:8080/users/{id}
   ```

### 결과 확인

각 요청을 수행한 후에 응답을 확인하여 원하는 대로 API가 작동하는지 검증합니다. **Postman**과 같은 도구를 사용하면 결과를 더 쉽게 확인할 수 있습니다.