package tech.openchat.telestore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @author vgorin
 * file created on 2020-04-08 20:13
 */

@Configuration
@EnableScheduling
public class Web3Config {
    private final String endpoint;

    public Web3Config(@Value("${web3.endpoint}") String endpoint) {
        this.endpoint = endpoint;
    }

    @Bean
    public Web3j createWeb3j() {
        return Web3j.build(new HttpService(endpoint));
    }
}
