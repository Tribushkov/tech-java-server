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
public class SignInServletTest {

    @NotNull
    private AccountService accountService = new AccountService();

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();


    @Test
    public void testDoPostOk() throws ServletException, IOException {

        System.out.println("Valid user!");
        accountService.addUser("email",
                        new UserProfile("email", "validUser", "password"));

        req.setParameter("email", "email");
        req.setParameter("password", "password");

        SignInServlet signInServlet = new SignInServlet(accountService);
        signInServlet.doPost(req, resp);

        assertEquals(HttpServletResponse.SC_OK, resp.getStatus());

    }

    @Test
    public void testDoPostWrongPassword() throws ServletException, IOException {
        System.out.println("User with wrong password!");
        accountService.addUser("email2",
                        new UserProfile("email2", "userWithWrongPassword", "wrongPassword"));


        req.setParameter("email", "email2");
        req.setParameter("password", "password");

        SignInServlet signInServlet = new SignInServlet(accountService);
        signInServlet.doPost(req, resp);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());
    }

    @Test
    public void testDoPostNullUser() throws ServletException, IOException {

        System.out.println("Null user!");


        req.setParameter("email", "email3");
        req.setParameter("password", "password1");

        SignInServlet signInServlet = new SignInServlet(accountService);
        signInServlet.doPost(req, resp);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());

    }

}