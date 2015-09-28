package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dmitri on 23.09.15.
 */
public class AdminServlet extends HttpServlet {

    @NotNull
    private AccountService accountService;

    public AdminServlet(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      @NotNull HttpServletResponse response) throws ServletException, IOException {

        Map<String, UserProfile> users = accountService.getUsers();
        Map<String, UserProfile> sessions = accountService.getSessions();

        JSONObject usersResponce = new JSONObject();

        for (Map.Entry<String, UserProfile> entry : users.entrySet()) {
            usersResponce.accumulate("users", entry.getKey());
        }

        for (Map.Entry<String, UserProfile> entry : sessions.entrySet()) {
            usersResponce.accumulate("sessions", entry.getValue().getEmail());
        }

        response.setContentType("application/json");
        response.getWriter().print(usersResponce.toString());
        response.getWriter().flush();


    }
}
