package frontend;

import main.AccountService;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitri on 22.09.15.
 */
public class LogOutServlet  extends HttpServlet {
    private AccountService accountService;

    public LogOutServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        assert request != null;
        String session = request.getSession().getId();

        assert accountService != null;
        if(accountService.deleteSession(session)) {
            assert response != null;
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            assert response != null;
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
