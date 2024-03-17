package ua.foxminded.springbootconsoleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.sql.DataSource;
import org.springframework.context.ConfigurableApplicationContext;
import ua.foxminded.springbootconsoleapp.ConsoleInteractionService;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootConsoleAppApplication {

   
	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootConsoleAppApplication.class, args);
        ConsoleInteractionService interactionService = context.getBean(ConsoleInteractionService.class);
        interactionService.ConsoleInteractionService();
    }
	

    @Bean
    public ConsoleInteractionService consoleInteractionService(Connection connection, StudentDao studentDao, GroupDao groupDao, CourseDao courseDao) {
        return new ConsoleInteractionService(connection, studentDao, groupDao, courseDao);
    }
}
