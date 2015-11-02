package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 02.11.15.
 */
public class CheckSignInServletTest {

    @NotNull
    private AccountService accountService = new AccountService();

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();

    int index;

    void preparing() {
        Random rand = new Random();
        index = rand.nextInt(2);

        switch (index) {
            case 0:
                UserProfile user = new UserProfile("email", "login", "password");
                req.getSession(true);
                accountService.addSession(req.getSession().getId(), user);
                break;
            case 1:
                break;
        }
    }

    @Test
    public void testDoPost() throws Exception {
        preparing();

        CheckSignInServlet checkSignInServlet = new CheckSignInServlet(accountService);

        checkSignInServlet.doPost(req, resp);
        switch (index) {
            case 0:
                System.out.println("Response is OK");
                assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
                break;
            case 1:
                System.out.println("Response is NOT OK");
                assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());
                break;
        }
    }
}