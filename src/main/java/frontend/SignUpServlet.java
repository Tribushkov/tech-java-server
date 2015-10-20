package frontend;

import main.UserProfile;
import main.AccountService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.jetbrains.annotations.NotNull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {

    @NotNull
    private final AccountService accountService;


    public SignUpServlet(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");

        if (accountService.getUser(email) == null) {
            if (!password1.equals(password2)) {
                resp.setHeader("Error", "1");
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else {
                accountService.addUser(email, new UserProfile(email, login, password1));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            resp.setHeader("Error", "2");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
