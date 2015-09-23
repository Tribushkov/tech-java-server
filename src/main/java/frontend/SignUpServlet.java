package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by v.chibrikov on 13.09.2014.
 */
public class SignUpServlet extends HttpServlet {

    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
//        String name = request.getParameter("name");
//        String password = request.getParameter("password");

//        if (accountService.addUser(name, new UserProfile(name, password, ""))) {
//            pageVariables.put("signUpStatus", "New user created");
//        } else {
//            pageVariables.put("signUpStatus", "User with name: " + name + " already exists");
//        }


        Map<String, Object> pageVariables = new HashMap<>();
        assert response != null;
        response.getWriter().println(PageGenerator.getPage("signup.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        assert response != null;
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("login", login == null ? "" : login);
        pageVariables.put("email", email == null ? "" : email);
        pageVariables.put("password", (!password1.equals(password2)) || (password1 == null) || (password2 == null) ? "" : password1);

        String redirectPage = "answer.html";
        if (isParamsCorrect(login, email, password1, password2)) {

            assert accountService != null;
            if(accountService.getUser(email) == null) {
                accountService.addUser(email, new UserProfile(login, password1, email));
                redirectPage = "success.html";
            } else {
                pageVariables.put("message", "User already exists!");
            }

        } else {
            pageVariables.put("message", "WRONG DATA");
        }
        response.getWriter().println(PageGenerator.getPage(redirectPage, pageVariables));
    }

    private boolean isParamsCorrect(String login, String email, String password1, String password2) {
        assert login != null;
        if (login.isEmpty())
            return false;

        assert email != null;
        if (email.isEmpty())
            return false;

        assert password1 != null;
        if (!password1.equals(password2))
            return false;

        if (password1.isEmpty())
            return false;

        if (password2.isEmpty())
            return false;

        return true;
    }
}
