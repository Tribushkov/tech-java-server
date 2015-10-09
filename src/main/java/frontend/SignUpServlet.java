package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    @NotNull
    private AccountService accountService;


    public SignUpServlet(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String password = req.getParameter("password1");

        if (accountService.getUser(email) == null) {
            accountService.addUser(email, new UserProfile(email, login, password));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setHeader("Error", "2");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
