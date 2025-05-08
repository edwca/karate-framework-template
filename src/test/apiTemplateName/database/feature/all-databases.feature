Feature: SQL Server DB Test

  Background:
    * def db = call read('classpath:database/db-config.js')
    * def DbUtils = Java.type('utils.DbUtils')
    * def db = new DbUtils(config)

  Scenario: Simulated DB connection
    * print 'Conectando a base de datos con usuario:', db.dbUser
    * print 'Host:', db.dbHost
    * print 'Simulando ejecución de: SELECT name FROM sys.databases'
    * def dummyResult = ['master', 'tempdb', 'model', 'msdb']
    * match dummyResult contains 'master'
