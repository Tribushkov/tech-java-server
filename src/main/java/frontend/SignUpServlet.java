package frontend;

import main.AccountService;
import main.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by v.chibrikov on 13.09.2014.
 */
public class SignUpServlet extends HttpServlet {

    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        assert request != null;
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");

        assert response != null;
        response.setStatus(HttpServletResponse.SC_OK);


            assert accountService != null;
            if(accountService.getUser(email) == null) {
                accountService.addUser(email, new UserProfile(login, password1, email));
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setHeader("Error", "2");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
    }
}
