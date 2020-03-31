package tech.openchat.telestore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author vgorin
 * file created on 2020-03-29 18:02
 */

@Configuration
@EnableAsync
public class TelegramBotConfig {
    @Bean("telegramBotExecutor")
    public ExecutorService singleThreadedExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
