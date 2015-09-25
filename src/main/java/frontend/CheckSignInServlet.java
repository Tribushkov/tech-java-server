package frontend;

import main.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dmitri on 25.09.15.
 */
public class CheckSignInServlet extends HttpServlet {

    private AccountService accountService;

    public CheckSignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        assert request != null;
        String session = request.getSession().getId();
        assert accountService != null;
        if (accountService.getSession(session) != null) {
            assert response != null;
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            assert response != null;
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
