package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 02.11.15.
 */
public class LogOutServletTest {

    @NotNull
    private AccountService accountService = new AccountService();

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();
    MockHttpSession session = new MockHttpSession();

    @Test
    public void testDoPost() throws Exception {
        Random rand = new Random();
        int index = rand.nextInt(2);

        UserProfile user;
        switch (index) {
            case 0:
                req.setSession(session);
                user = new UserProfile("email", "login", "password");
                accountService.addSession(session.getId(), user);
                break;
            case 1:
                req.setSession(session);
                break;
        }


        LogOutServlet logOutServlet = new LogOutServlet(accountService);
        logOutServlet.doPost(req, resp);

        switch (index) {
            case 0:
                System.out.println("Session deleted");
                assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
                break;
            case 1:
                System.out.println("Session doesn't exists");
                assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, resp.getStatus());
                break;
        }
    }
}