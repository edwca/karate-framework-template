Feature: Validar conexión a SQL Server

  Background:

    ## db consume trae por defecto toda la configuracion seteada en karate-config.js
    * def db = config.db

  Scenario: Validar que existen bases de datos
    * def result = db.query("SELECT name FROM sys.databases")
    * print result
    * match result == '#notnull'