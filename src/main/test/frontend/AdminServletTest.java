package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 01.11.15.
 */
public class AdminServletTest {

    @NotNull
    private final AccountService accountService = new AccountService();
    @NotNull
    private final UserProfile testUser = new UserProfile("test@test.test", "testLogin", "testPassword");
    private final UserProfile testUser2 = new UserProfile("test2@test.test", "testLogin2", "testPassword2");


    // TODO: WTF?
    @Test
    public void testDoGet() throws Exception {
        accountService.addUser(testUser.getEmail(), testUser);
        accountService.addUser(testUser2.getEmail(), testUser2);

        String sessionId = "testId";
        String sessionId2 = "testId2";
        accountService.addSession(sessionId, testUser);
        accountService.addSession(sessionId2, testUser2);


    }
}