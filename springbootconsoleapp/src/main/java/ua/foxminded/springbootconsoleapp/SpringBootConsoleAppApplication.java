package ua.foxminded.springbootconsoleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ua.foxminded.springbootconsoleapp.config.*;
import java.sql.SQLException;

@SpringBootApplication
public class SpringBootConsoleAppApplication {

   
	public static void main(String[] args) throws SQLException {
		  ConfigurableApplicationContext context = SpringApplication.run(SpringBootConsoleAppApplication.class, args);
	        LoggingController loggingController = context.getBean(LoggingController.class);
	        loggingController.displayMenu();
        
    }


}
