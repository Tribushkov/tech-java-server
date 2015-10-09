package frontend;

import main.UserProfile;
import main.AccountService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.jetbrains.annotations.NotNull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SignInServlet extends HttpServlet {

    @NotNull
    private AccountService accountService;


    public SignInServlet(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserProfile user = accountService.getUser(email);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                if (accountService.getSession(req.getSession().getId()) == null)
                    accountService.addSession(req.getSession().getId(), user);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setHeader("Error", "0");
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            resp.setHeader("Error", "1");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
