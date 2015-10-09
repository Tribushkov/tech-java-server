package main;

import javax.servlet.Servlet;
import frontend.SignUpServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class Main {
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(Integer.parseInt(args[0]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You have to give a port number!"); return;
        } catch (NumberFormatException e) {
            System.out.println("The port number is a number!"); return;
        }

        AccountService accountService = new AccountService();

        Servlet signup = new SignUpServlet(accountService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(signup), "/signin");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, contextHandler});

        server.setHandler(handlers);
        
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println("Server failed");
        }
    }
}
