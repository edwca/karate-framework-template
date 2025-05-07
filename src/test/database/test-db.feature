Feature: Test SQL Server connection

  Background:
    * def config = call read('classpath:database/db-config.js')
    * def dbUtils = Java.type('utils.DbUtils')
    * def db = new dbUtils(config)

  Scenario: Validate DB connection
    * def result = db.select('SELECT name FROM sys.databases')
    * print result
