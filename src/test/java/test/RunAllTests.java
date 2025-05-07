package test;

import com.intuit.karate.junit5.Karate;

public class RunAllTests {
  @Karate.Test
  Karate testAll() {
    return Karate.run(
      //Desde src/test/java/test (donde está RunAllTests.java) Subimos 3 niveles (../../../), entramos a apiTemplateName/login/... y apiTemplateName/user/...
        "../../../apiTemplateName/login/post/features/login",
        "../../../apiTemplateName/user/post/features/create-user").relativeTo(getClass());
  }
}

// Global configuration almacenar los features en apiTemplateName
// @Karate.Test
// Karate testAll() {
//   return Karate.run("../../../apiTemplateName").relativeTo(getClass());
// }