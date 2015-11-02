package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

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

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();
    MockHttpServletResponse expected_resp = new MockHttpServletResponse();

    void initializeExpectedResponse() {
        JSONObject json = new JSONObject();

        for (Map.Entry<String, UserProfile> entry : accountService.getUsers().entrySet()) {
            json.accumulate("users", entry.getKey());
        }

        for (Map.Entry<String, UserProfile> entry : accountService.getSessions().entrySet()) {
            json.accumulate("sessions", entry.getValue().getEmail());
        }

        expected_resp.setContentType("application/json");
        expected_resp.addHeader("answer", json.toString());
    }

    // TODO: WTF?
    @Test
    public void testDoGet() throws ServletException, IOException {
        accountService.addUser(testUser.getEmail(), testUser);
        accountService.addUser(testUser2.getEmail(), testUser2);

        String sessionId = "testId";
        accountService.addSession(sessionId, testUser);
        String sessionId2 = "testId2";
        accountService.addSession(sessionId2, testUser2);

        initializeExpectedResponse();

        AdminServlet adminServlet = new AdminServlet(accountService);
        adminServlet.doGet(req, resp);

        assertEquals(expected_resp.getHeader("answer"), resp.getHeader("answer"));
        System.out.println(expected_resp.getHeader("answer"));
        System.out.println(resp.getHeader("answer"));
    }
}