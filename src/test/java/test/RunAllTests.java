package test;

import com.intuit.karate.junit5.Karate;

class RunAllTests {

  @Karate.Test
  Karate testAll() {
        // Toma la ruta dentro del classpath es decir dentro de target/test-classes
    return Karate.run("classpath:apiTemplateName/login/post/features/login.feature");
  }

}