package frontend;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import main.AccountService;
import main.UserProfile;
import org.eclipse.jetty.client.HttpResponse;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Server;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitri on 23.09.15.
 */
public class AdminServlet extends HttpServlet {

    private Server server;
    private AccountService accountService;

    public AdminServlet(Server server, AccountService accountService) {
        this.server = server;
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

//        long shutDownTime = 0;
//        try {
//            assert request != null;
//            shutDownTime = Long.parseLong(request.getParameter("shutdown"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        if (shutDownTime != 0) {
//            assert server != null;
//            server.setStopTimeout(shutDownTime);
//            try {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        try {
//                            server.stop();
//                        } catch (Exception ex) {
//                            System.out.println("Failed to stop Jetty");
//                        }
//                    }
//                }.start();
//            } catch (RuntimeException e) {
//                e.printStackTrace();
//            }
//        } else {

            Map<String, UserProfile> users = accountService.getUsers();
            Map<String, UserProfile> sessions = accountService.getSessions();

            JSONObject usersResponce = new JSONObject();
            JSONObject usersJSON = new JSONObject();
            JSONObject sessionsJSON = new JSONObject();

            for (Map.Entry<String, UserProfile> entry : users.entrySet())
            {
                usersResponce.accumulate("users", entry.getKey());
            }

            for (Map.Entry<String, UserProfile> entry : sessions.entrySet())
            {
                usersResponce.accumulate("sessions", entry.getValue().getEmail());
            }

            assert response != null;
            response.setContentType("application/json");
            response.getWriter().print(usersResponce.toString());
            response.getWriter().flush();

//        }
    }
}
