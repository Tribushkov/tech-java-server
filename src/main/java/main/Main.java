package main;

import frontend.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

/**
 * @author v.chibrikov
 */

public class Main {
    public static void main(String[] args) throws Exception, NumberFormatException, InterruptedException {

        if (args.length == 1) {
            String portString = args[0];

            int port = Integer.valueOf(portString);


            Server server = new Server(port);

            System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

            AccountService accountService = new AccountService();

            Servlet signin = new SignInServlet(accountService);
            Servlet signUp = new SignUpServlet(accountService);
            Servlet logOut = new LogOutServlet(accountService);
            Servlet admin = new AdminServlet(accountService);
            Servlet checkSignIn = new CheckSignInServlet(accountService);
            Servlet stopServer = new StopServerServlet(server);

            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.addServlet(new ServletHolder(signin), "/signin");
            context.addServlet(new ServletHolder(signUp), "/signup");
            context.addServlet(new ServletHolder(logOut), "/logout");
            context.addServlet(new ServletHolder(admin), "/adminpage");
            context.addServlet(new ServletHolder(checkSignIn), "/islogged");
            context.addServlet(new ServletHolder(stopServer), "/admin");

            ResourceHandler resource_handler = new ResourceHandler();
            resource_handler.setDirectoriesListed(true);
            resource_handler.setResourceBase("public_html");

            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[]{resource_handler, context});

            server.setHandler(handlers);

            server.start();
            server.join();
        } else {
            System.out.println("Missed port parameter!");
        }
    }
}