package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

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
    public void testDoPostOk() throws ServletException, IOException {

        req.setSession(session);
        UserProfile user = new UserProfile("email", "login", "password");
        accountService.addSession(session.getId(), user);


        LogOutServlet logOutServlet = new LogOutServlet(accountService);
        logOutServlet.doPost(req, resp);


        System.out.println("Session deleted");
        assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
    }

    @Test
    public void testDoPostError() throws ServletException, IOException {
        req.setSession(session);

        LogOutServlet logOutServlet = new LogOutServlet(accountService);
        logOutServlet.doPost(req, resp);


        System.out.println("Session doesn't exists");
        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, resp.getStatus());

    }
}