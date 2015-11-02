package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 01.11.15.
 */
public class SignUpServletTest {

    @NotNull
    private AccountService accountService = new AccountService();
    private ArrayList<UserData> users;

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();

    private class UserData {
        public String email;
        public String login;
        public String password1;
        public String password2;

        UserData(String email, String login, String password1, String password2) {
            this.email = email;
            this.login = login;
            this.password1 = password1;
            this.password2 = password2;
        }
    }

    private void preparing() {
        users = new ArrayList<>();

        users.add(new UserData("email", "validUser", "password", "password"));
        users.add(new UserData("email2", "userWithUnequalPasswords", "password1", "password2"));
        users.add(new UserData("email3", "alreadyExistingUser", "password", "password"));
    }

    void setRequestParams(String email, String login, String password1, String password2) {
        req.setParameter("email", email);
        req.setParameter("login", login);
        req.setParameter("password1", password1);
        req.setParameter("password2", password2);
    }

    @Test
    public void testDoPost() throws Exception {

        preparing();

        Random rand = new Random();
        int index = rand.nextInt(3);

        UserProfile user = new UserProfile(users.get(index).email, users.get(index).login, users.get(index).password1);

        if (index == 2) {
            accountService.addUser(user.getEmail(), user);
        }

        setRequestParams(users.get(index).email, users.get(index).login,
                users.get(index).password1, users.get(index).password2);
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        signUpServlet.doPost(req, resp);

        switch (index) {
            case 0:
                assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
                System.out.println("Valid user!");
                break;
            case 1:
                assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());
                System.out.println("Unequal passwords!");
                break;
            case 2:
                assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());
                System.out.println("Already existing user!");
                break;
            default:
                break;
        }
    }
}