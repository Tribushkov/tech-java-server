package frontend;

import main.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignInServlet extends HttpServlet {
    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        assert request != null;
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        assert response != null;
        response.setStatus(HttpServletResponse.SC_OK);

        assert accountService != null;
        if (accountService.getUser(email) != null) {
            if (accountService.getUser(email).getPassword().equals(password)) {
                if (accountService.getSession(request.getSession().getId()) == null)
                    accountService.addSession(request.getSession().getId(), accountService.getUser(email));

                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setHeader("Error", "0");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.setHeader("Error", "1");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
