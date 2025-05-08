package test;

import com.intuit.karate.junit5.Karate;

class RunAllTests {

  @Karate.Test
  Karate testAll() {
    return Karate.run("login/post/features/login.feature").relativeTo(getClass());
  }

}