package apiTemplateName.login.post;

import com.intuit.karate.junit5.Karate;

class RunLoginTests {
    @Karate.Test
    Karate testLogin() {
        return Karate.run("features/login.feature").relativeTo(getClass());
    }
}
