Feature: Create user with reqres.in

  Background:
    * url BaseUrl
    * path 'api/users'
    * header Content-Type = 'application/json'
    * header x-api-key = 'reqres-free-v1'

  @REQRES-USER-01
  Scenario: Create user successfully
    * def payload = { name: 'morpheus', job: 'leader' }
    * def schema = read('schemas/create-user-success.json')
    Given request payload
    When method post
    Then status 201
    And match response == schema
    And match response.name == 'morpheus'
    And match response.job == 'leader'
    And match response.id == '#string'
    And match response.createdAt contains '2025'
