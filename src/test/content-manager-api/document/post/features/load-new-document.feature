Feature: CRUD Content Manager
    # Dato: Karate solo encuentra archivos si están dentro de un paquete válido o ruta relativa desde un runner Java o desde una feature dentro del mismo árbol,
    # por lo que para navegar debemos ir de desde feature hacia atras
    # classpath consume todo lo que en tiempo de ejecución se pasa a target/test-classes/, 
  
  Background:
    # Utlidades necesarias
    * def env = karate.env
    * def config = call read('classpath:karate-config.js')
    * def db = config.databases.oracle
    * def payload = read('classpath:content-manager-api/document/post/data/payload/carga-basica.json')
    * def validators = call read('classpath:common/validators.js')
    * def utils = call read('classpath:common/utils.js')
    
    # Pre-condidición 1:Ruta a utilizar en caso de prueba
    * def redPathFile = ContentRedPathFile
   
    #  Pre-condidición 2: Validacion de acceso a carpeta, agregamos operador ternario para saltar
    # validacion en ci/cd ya que desde el entorno linux no se tiene acceso a carpeta y assert falla
    * def skipFileCheck = (env != 'qa' && env != 'devel'  && env != 'pre-prod')
    * def dirCheck = skipFileCheck ? true : utils.directoryExists(redPathFile)
    * print '📁 Validando acceso a carpeta:', redPathFile, '| Resultado:', dirCheck
    * assert dirCheck == true

    # Pre-condidición 3: Eliminar archivo antes de iniciar desde ruta
    * def deleted = (env != 'qa' && env != 'devel') ? true : utils.deleteFileIfExists(redPathFile + '\\DOCUMENTO DE PRUEBA CONTENT.pdf')
    * print '🧹 Eliminado archivo previo:', deleted
  
    # Configuracion URl request y headers
    * url BaseUrlContent
    * path 'PkgContentManager/CargaPeriodicaBasica'
    * header Content-Type = 'application/json'

    @CONTENT-MANAGER @ignore
    Scenario: TS-00: Validate Document does not exist in route
    * def schema = read('../schemas/not-file-in-directory-response.json')
    # Envio paylod fijo
    Given request payload
    When method post
    Then status 200
    # Forma para validar todo un schema de respuesta (como contract testing)
    And match response == schema
    * print '📦 Response:', response
    # Forma para validar un tipo de dato para un parameto x
    And match response.Result.idcontent == '#null'
    * match response.Result.estadoCarga == 'NOK'
    * match response.Result.mensajeError contains 'no encontrado'

    @CONTENT-MANAGER
  Scenario: TS-01: Register a valid new document in Content Manager
    * def schema = read('../schemas/sucessful-response.json')
    # Copiar archivo en directory
    * def redPathFileCompleto = (env != 'qa' && env != 'devel') ? true : redPathFile + '\\DOCUMENTO DE PRUEBA CONTENT.pdf'
    * def ok = (env != 'qa' && env != 'devel') ? true : utils.copyFile('DOCUMENTO DE PRUEBA CONTENT.pdf', redPathFile)
    * match ok == true
    # Espera estática de 5 segundos: hasta que archivo esté realmente visible
    * eval java.lang.Thread.sleep(5000)
    # Envio paylod fijo
    Given request payload
    When method post
    Then status 200
    # Forma para validar todo un schema de respuesta (como contract testing)
    And match response == schema
    * print '📦 Response:', response.Result.idcontent
    * def idcontent = response.Result.idcontent
    # seteo global de variable para uso en otros scenarios
    * karate.set('idcontent', idcontent)
    * def query = "SELECT * FROM CONTENT.cm_contenmanager WHERE SCOD_CONTENT = '" + idcontent + "'"
    * print '🧪 Ejecutando query:', query
    * def result = db.query(query)
    * print '📦 Resultado DB:', result
    * match result[0].SCOD_CONTENT == idcontent
    # Forma para validar un tipo de dato para un parameto x
    And match response.Result.idcontent == '#string'
    # Validar a traves de funciones utilitarias el tipo de dato --> forma mas modular y recomendada (de forma robusta)
    * match validators.isStringOrNull(response.idcontent) == true

    #   @TEARDOWN
    # Scenario: Limpiar entorno post-ejecución
    #   * def idcontent = karate.get('idcontent')
    #   * print '🧹 Eliminando registro para idcontent:', idcontent

    #   # Eliminar archivo
    #   * def filePath = '\\\\messnerdesa\\Lexmark\\DOCUMENTO DE PRUEBA CONTENT.pdf'
    #   * def deletedFile = utils.deleteFileIfExists(filePath)
    #   * match deletedFile == true

    #   # Eliminar en base de datos
    #   * def query = "DELETE FROM CONTENT.cm_contenmanager WHERE SCOD_CONTENT = '" + idcontent + "'"
    #   * def deletedDb = db.update(query)
    #   * print '🗑️ Registros eliminados:', deletedDb

