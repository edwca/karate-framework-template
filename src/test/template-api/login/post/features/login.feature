Feature: Reqres login

  Background:
    ## uso data dinamica
    # * def dynamicData = call read('classpath:apiTemplateName/login/post/data/dynamic-data.js')
    # * def email = dynamicData.email
    # * def password = dynamicData.password
    # * print '➡️  Login dinámico con', email, password
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

  @REQRES-LOGIN
  Scenario: TS-00: Successful login should return a token and validate schema
    * def schema = read('../schemas/login-successful.json')
    Given request credentials
    When method post
    Then status 200
    And match response == schema
    And match response.token == '#string'


  @REQRES-LOGIN
  Scenario: TS-00: Missing password should return 400 and error schema
    * def email = reqresLoginEmail
    * def credentials = { email: email }
    * def schema = read('../schemas/login-error.json')
    Given request credentials
    When method post
    Then status 400
    And match response == schema

  @REQRES-LOGIN
  Scenario: TS-00: Invalid user should return 400 with unauthorized schema
    * def credentials = { email: 'wrong@email.com', password: 'wrongpass' }
    * def schema = read('../schemas/login-unauthorized.json')
    Given request credentials
    When method post
    Then status 400
    And match response == schema
