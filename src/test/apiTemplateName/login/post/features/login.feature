Feature: Reqres login

  Background:
    * def email = reqresLoginEmail
    * def password = reqresLoginPassword
    * def credentials = { email: "#(email)", password: "#(password)" }  
    * print '✅ Email:', email
    * print '✅ Password:', password
    * print '📦 Payload:', credentials
    * url BaseUrl
    * path 'api/login'
    * header Content-Type = 'application/json'
    * header x-api-key = 'reqres-free-v1'

  @REQRES-LOGIN-01
  Scenario: Successful login should return a token and validate schema
    * def schema = read('../schemas/login-successful.json')
    Given request credentials
    When method post
    Then status 200
    And match response == schema
    And match response.token == '#string'


@REQRES-LOGIN-02
Scenario: Missing password should return 400 and error schema
  * def email = reqresLoginEmail
  * def credentials = { email: email }
  * def schema = read('../schemas/login-error.json')
  Given request credentials
  When method post
  Then status 400
  And match response == schema

@REQRES-LOGIN-03
Scenario: Invalid user should return 400 with unauthorized schema
  * def credentials = { email: 'wrong@email.com', password: 'wrongpass' }
  * def schema = read('../schemas/login-unauthorized.json')
  Given request credentials
  When method post
  Then status 400
  And match response == schema
