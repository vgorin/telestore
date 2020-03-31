package tech.openchat.telestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TelestoreApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(TelestoreApplication.class, args);
    }

}
