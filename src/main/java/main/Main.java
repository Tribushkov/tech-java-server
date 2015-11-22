package main;

import base.GameMechanics;
import base.WebSocketService;
import frontend.*;
import javax.servlet.Servlet;

import mechanics.GMResources;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import utils.ResourceFactory;
import utils.ServerCfgHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Server server = null;
        try {
//            server = new Server(Integer.parseInt(args[0]));
            ServerCfgHelper properties = new ServerCfgHelper();
            HashMap<String, String> pr = properties.getPropValues();

            Configuration configuration = new Configuration(pr.get("host"), Integer.valueOf(pr.get("port")));

            InetSocketAddress address = new InetSocketAddress(configuration.getHost(), configuration.getPort());
            server = new Server(address);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You have to give a port number!"); return;
        } catch (NumberFormatException e) {
            System.out.println("The port number is a number!"); return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        AccountService accountService = new AccountService();
        WebSocketService webSocketService = new WebSocketServiceImpl();

        GMResources gmResources = (GMResources) ResourceFactory.getInstance().getResourceObject("data/game_data.xml");
        assert gmResources != null;
        gmResources.getColors().remove(0);
        gmResources.getColors().remove(0);
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService, gmResources);

        Servlet signUp = new SignUpServlet(accountService);
        Servlet signIn = new SignInServlet(accountService);
        Servlet logOut = new LogOutServlet(accountService);
        Servlet admin  = new AdminServlet(accountService);
        Servlet check  = new CheckSignInServlet(accountService);
        assert server != null;
        Servlet stop   = new StopServerServlet(server);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(signUp), "/signup");
        contextHandler.addServlet(new ServletHolder(signIn), "/signin");
        contextHandler.addServlet(new ServletHolder(logOut), "/logout");
        contextHandler.addServlet(new ServletHolder(admin), "/adminpage");
        contextHandler.addServlet(new ServletHolder(check), "/islogged");
        contextHandler.addServlet(new ServletHolder(stop), "/admin");
        contextHandler.addServlet(new ServletHolder(new GameServlet(gameMechanics, accountService)), "/gamewating");
        contextHandler.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, gameMechanics,
                webSocketService)), "/game");


        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, contextHandler});

        server.setHandler(handlers);


        //noinspeection TryWithIdenticalCatches
        try {
            server.start();
//            server.join();
            gameMechanics.run();
        } catch (Exception e) {
            System.out.println("Server failed");
        }
    }
}
