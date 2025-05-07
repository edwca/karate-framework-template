function() {
  var DbUtils = Java.type('utils.DbUtils');
  var dbConfig = {
    user: karate.env == 'qa' ? 'qa_user' : 'dev_user',
    password: karate.env == 'qa' ? 'qa_pass' : 'dev_pass',
    server: 'localhost',
    database: 'karate_test_db',
    encrypt: true,
    trustServerCertificate: true
  };

  var config = {
    BaseUrl: java.lang.System.getenv('BASE_URL') || 'https://reqres.in',
    companyNumber: 1,
    storeNumber: 2,
    posNumber: 3,
    serverVersion: '3.0.0-dev',
    clientSoftwareVersion: 'karate.hten:1.0.0',
    waitForTransactions: 2000,
    reqresLoginEmail: java.lang.System.getenv('reqresLoginEmail'),
    reqresLoginPassword: java.lang.System.getenv('reqresLoginPassword'),
    gnetpos: {
      serviceName: 'gnetposapi',
      companyNumber: 1,
      storeNumber: 2,
      posNumber: 4,
      terminalId: '80000109',
      retailerId: '13382',
      serialNumber: '22B7KD8G4627',
      waitForPinpad: 30000
    },
    db: new DbUtils(dbConfig)
  };

  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 10000);

  return config;
}
