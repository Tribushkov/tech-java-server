package frontend;

import java.util.Map;

import base.dataSets.UserDataSet;
import main.UserProfile;
import main.AccountService;
import org.json.JSONObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.jetbrains.annotations.NotNull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {

    @NotNull
    private final AccountService accountService;

    public AdminServlet(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, UserDataSet> users = accountService.getUsers();
        Map<String, UserDataSet> sessions = accountService.getSessions();

        JSONObject json = new JSONObject();

        for (Map.Entry<String, UserDataSet> entry : users.entrySet()) {
            json.accumulate("users", entry.getKey());
        }

        for (Map.Entry<String, UserDataSet> entry : sessions.entrySet()) {
            json.accumulate("sessions", entry.getValue().getEmail());
        }

        resp.setContentType("application/json");
        resp.addHeader("answer", json.toString());
        resp.getWriter().print(json.toString());
        resp.getWriter().flush();
    }

}
