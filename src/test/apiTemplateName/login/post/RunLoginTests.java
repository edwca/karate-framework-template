package apiTemplateName.login.post;

import com.intuit.karate.junit5.Karate;

class RunLoginTests {
    @Karate.Test
    Karate testLogin() {
        return Karate.run("apiTemplateName/login/post/features").relativeTo(getClass());
    }
}
