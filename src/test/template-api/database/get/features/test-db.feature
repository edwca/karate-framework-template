    # Feature: Validar conexión a Oracle y obtener filas desde CM_CONTENMANAGER

    #   Background:
    #     * def config = call read('classpath:karate-config.js')
    #     * def db = config.db

    #   Scenario: Obtener primeras 10 filas y validar contenido
    #     * def out =
    #       """
    #       function() {
    #         try {
    #           var rows = db.query("SELECT * FROM CM_CONTENMANAGER WHERE ROWNUM <= 10");
    #           karate.log('✅ Query ejecutada. Filas:', rows.length);
    #           return { success: true, rows: rows };
    #         } catch(e) {
    #           karate.log('❌ Error:', e);
    #           return { success: false, error: e + '' };
    #         }
    #       }
    #       """
    #     * def result = call out
    #     * print '🧪 Resultado:', result
    #     # llamada exitosa a la db
    #     * match result.success == true
    #     # Verifica que result.rows sea un array que contenga exactamente 10 elementos
    #     * match result.rows == '#[10]'
    #     #Verifica que la primera fila (index 0) del array rows tenga la propiedad SIMAGEN y que no sea null ni
    #     * match result.rows[0].SIMAGEN == '#notnull'

Feature: Validate data in oracle db

  Background:
    * def config = call read('classpath:karate-config.js')
    # pasamos la db a utilizar
    * def db = config.databases.oracle
    
  @DATABASE
  Scenario:  TS-00: Get data from CM_CONTENMANAGER
    * def result = db.query("SELECT * FROM CM_CONTENMANAGER WHERE ROWNUM <= 10")
    * print '🗂 Resultados:', result
    * match result == '#[10]'






