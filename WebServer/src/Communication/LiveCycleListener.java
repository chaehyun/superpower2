package Communication;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


public class LiveCycleListener<MyServer> implements ServletContextListener {
	
	MyServer instance;

    /**
     * Default constructor. 
     */
    public LiveCycleListener() {
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	/*
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			System.out.println("Shutting down is started.");
			instance.getStudents().save();
			instance.getCompanies().save();
			instance.getRequests().save();
			System.out.println("Context destroyed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Context Initialization started.");
		instance = MyServer.getInstance();
		instance.init();
	}

	*/
}
