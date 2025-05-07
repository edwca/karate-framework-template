function() {
  var faker = Java.type('com.github.javafaker.Faker');
  var f = new faker();
  return {
    name: f.name().fullName(),
    email: f.internet().emailAddress(),
    password: f.internet().password()
  };
}