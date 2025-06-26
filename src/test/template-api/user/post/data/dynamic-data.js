function generateData() {
  var faker = Java.type('com.github.javafaker.Faker');
  var f = new faker();
  return {
    email: f.internet().emailAddress(),
    password: f.internet().password()
  };
}