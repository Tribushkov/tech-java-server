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
 * Created by dmitri on 02.11.15.
 */
public class SignInServletTest {

    @NotNull
    private AccountService accountService = new AccountService();
    private ArrayList<UserData> users;

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();

    private class UserData {
        public String email;
        public String login;
        public String password;

        UserData(String email, String login, String password) {
            this.email = email;
            this.login = login;
            this.password = password;
        }
    }

    private void preparing() {
        users = new ArrayList<>();

        users.add(new UserData("email", "validUser", "password"));
        users.add(new UserData("email2", "userWithWrongPassword", "password1"));
        users.add(new UserData("email3", "nullUser", "password1"));
    }

    @Test
    public void testDoPost() throws Exception {
        preparing();

        Random rand = new Random();
        int index = rand.nextInt(3);

        switch (index) {
            case 0:
                System.out.println("Valid user!");
                accountService.addUser(users.get(index).email,
                        new UserProfile(users.get(index).email, users.get(index).login, users.get(index).password));
            break;
            case 1:
                System.out.println("User with wrong password!");
                accountService.addUser(users.get(index).email,
                        new UserProfile(users.get(index).email, users.get(index).login, "wrongPassword"));
                break;
            case 2:
                System.out.println("Null user!");
                break;
            default:
                break;
        }

        req.setParameter("email", users.get(index).email);
        req.setParameter("password", users.get(index).password);

        SignInServlet signInServlet = new SignInServlet(accountService);
        signInServlet.doPost(req, resp);

        switch (index) {
            case 0:
                assertEquals(HttpServletResponse.SC_OK, resp.getStatus());
                break;
            case 1:
                assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());
                break;
            case 2:
                assertEquals(HttpServletResponse.SC_FORBIDDEN, resp.getStatus());
                break;
        }
    }

}