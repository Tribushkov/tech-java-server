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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setStatus(HttpServletResponse.SC_OK);

        if (accountService.getUser(email) != null) {
            if (accountService.getUser(email).getPassword().equals(password)) {
                if (accountService.getSession(request.getSession().getId()) == null)
                    accountService.addSession(request.getSession().getId(), accountService.getUser(email));
                    response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setHeader("Error", "Wrong password");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setHeader("Error", "User doesn't exist");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
