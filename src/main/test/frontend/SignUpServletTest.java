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
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 01.11.15.
 */
public class SignUpServletTest {

    @NotNull
    private AccountService accountService = new AccountService();

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();


    void setRequestParams(String email, String login, String password1, String password2) {
        req.setParameter("email", email);
        req.setParameter("login", login);
        req.setParameter("password1", password1);
        req.setParameter("password2", password2);
    }

    @Test
    public void testDoPostOk() throws ServletException, IOException {
        setRequestParams("email", "validUser", "password", "password");
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        signUpServlet.doPost(req, resp);

        assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
        System.out.println("Valid user!");
    }

    @Test
    public void testDoPostUnequalPasswords() throws ServletException, IOException {
        setRequestParams("email2", "userWithUnequalPasswords", "password1", "password2");
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        signUpServlet.doPost(req, resp);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());
        System.out.println("Unequal passwords!");

    }

    @Test
    public void testDoPostAlreadyExistingUser() throws ServletException, IOException {
        UserProfile user = new UserProfile("email3", "alreadyExistingUser", "password");

        accountService.addUser(user.getEmail(), user);

        setRequestParams("email3", "alreadyExistingUser", "password", "password");
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        signUpServlet.doPost(req, resp);


        assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());
        System.out.println("Already existing user!");
    }
}