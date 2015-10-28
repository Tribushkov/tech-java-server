package main;

import base.GameMechanics;
import base.WebSocketService;
import frontend.*;
import javax.servlet.Servlet;

import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import utils.ReadXMLSax;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        Server server;
        try {
//            server = new Server(Integer.parseInt(args[0]));
            Configuration configuration = (Configuration) ReadXMLSax.readXML("cfg/config.xml");
            InetSocketAddress address = new InetSocketAddress(configuration.getHost(), configuration.getPort());
            server = new Server(address);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You have to give a port number!"); return;
        } catch (NumberFormatException e) {
            System.out.println("The port number is a number!"); return;
        }

        AccountService accountService = new AccountService();
        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

        Servlet signUp = new SignUpServlet(accountService);
        Servlet signIn = new SignInServlet(accountService);
        Servlet logOut = new LogOutServlet(accountService);
        Servlet admin  = new AdminServlet(accountService);
        Servlet check  = new CheckSignInServlet(accountService);
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
        } catch (InterruptedException e) {
            System.out.println("Server failed");
        } catch (Exception e) {
            System.out.println("Server failed");
        }
    }
}
