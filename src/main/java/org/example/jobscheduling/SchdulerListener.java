package org.example.jobscheduling;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;

@WebListener
public class SchdulerListener
    implements ServletContextListener{
        @Override
                public void contextInitialized(ServletContextEvent event)
        {
         jobscheduling.start();
         System.out.println("Started");
        }
        @Override
                public void contextDestroyed(ServletContextEvent event)
        {
         jobscheduling.stop();
         System.out.println("Started");
        }

}
