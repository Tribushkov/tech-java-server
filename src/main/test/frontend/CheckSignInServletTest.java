package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 02.11.15.
 */
public class CheckSignInServletTest {

    @NotNull
    private AccountService accountService = new AccountService();

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();

    @Test
    public void testDoPostOk() throws ServletException, IOException {

        UserProfile user = new UserProfile("email", "login", "password");
        req.getSession(true);
        accountService.addSession(req.getSession().getId(), user);

        CheckSignInServlet checkSignInServlet = new CheckSignInServlet(accountService);

        checkSignInServlet.doPost(req, resp);
        System.out.println("Response is OK");
        assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
    }

    @Test
    public void testDoPostForbidden() throws ServletException, IOException {

        CheckSignInServlet checkSignInServlet = new CheckSignInServlet(accountService);

        checkSignInServlet.doPost(req, resp);
        System.out.println("Response is NOT OK");
        assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());

    }
}