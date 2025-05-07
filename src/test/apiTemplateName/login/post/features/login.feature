Feature: Reqres login

  Background:
    * url BaseUrl
    * path 'api/login'
    * header Content-Type = 'application/json'
    * header x-api-key = 'reqres-free-v1'

  @REQRES-LOGIN-01
  Scenario: Successful login should return a token and validate schema
    * def credentials = { email: karate.properties['reqresLoginEmail'], password: karate.properties['reqresLoginPassword'] }
    * def schema = read('schemas/login-successful.json')
    Given request credentials
    When method post
    Then status 200
    And match response == schema
    And match response.token == '#string'

  @REQRES-LOGIN-02
  Scenario: Missing password should return 400 and error schema
    * def credentials = { email: karate.properties['reqresLoginEmail'] }
    * def schema = read('schemas/login-error.json')
    Given request credentials
    When method post
    Then status 400
    And match response == schema

  @REQRES-LOGIN-03
  Scenario: Invalid user should return 400 with unauthorized schema
    * def credentials = { email: 'wrong@email.com', password: 'wrongpass' }
    * def schema = read('schemas/login-unauthorized.json')
    Given request credentials
    When method post
    Then status 400
    And match response == schema
