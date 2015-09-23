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

        String session = request.getSession().getId();


        Map<String, Object> pageVariables = new HashMap<>();
        if(accountService.deleteSession(session)) {
            pageVariables.put("message", "You've been logged out.");
        } else {
            pageVariables.put("message", "You've been already logged out.");
        }

        response.getWriter().println(PageGenerator.getPage("answer.html", pageVariables));
    }

}
