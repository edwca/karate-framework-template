package apiTemplateName.user.post;

import com.intuit.karate.junit5.Karate;

class RunUserTests {
    @Karate.Test
    Karate testUser() {
        return Karate.run("apiTemplateName/user/post/features").relativeTo(getClass());
    }
}
