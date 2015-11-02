package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

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
//    private UserProfile createUser(String email, String login, String password) {
//        return User
//    }

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

    @Test
    public void testDoPost() throws Exception {

        preparing();

        Random rand = new Random();
        int index = rand.nextInt(3);

        UserProfile user = new UserProfile(users.get(index).email, users.get(index).login, users.get(index).password1);

        if (index == 2) {
            accountService.addUser(user.getEmail(), user);
        }

        if (accountService.getUser(user.getEmail()) == null) {
            if (!users.get(index).password1.equals(users.get(index).password2)) {
                System.out.println("Unequal passwords!");
                assertEquals(true, true);
            } else {
                System.out.println("Valid user!");
                accountService.addUser(user.getEmail(), user);
                assertEquals(user, accountService.getUser(user.getEmail()));
            }
        } else {
            System.out.println("Already existing user!");
            assertEquals(user.getEmail(), accountService.getUser(user.getEmail()).getEmail());
        }
    }
}