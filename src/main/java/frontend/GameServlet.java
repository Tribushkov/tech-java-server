package frontend;

import base.GameMechanics;
import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitri on 24.10.15.
 */

public class GameServlet extends HttpServlet {

    @NotNull
    private GameMechanics gameMechanics;

    @NotNull
    private AccountService accountService;

    public GameServlet(@NotNull GameMechanics gameMechanics, @NotNull AccountService accountService) {
        this.gameMechanics = gameMechanics;
        this.accountService = accountService;
    }


    @Override
    public void doPost(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response) throws ServletException, IOException {

        try {
            String id = request.getSession().getId();

            if (accountService.getSession(id) == null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else {
                String name = accountService.getSession(id).getEmail();
                this.gameMechanics.addUser(name);
                response.addHeader("EMAIL", name);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
