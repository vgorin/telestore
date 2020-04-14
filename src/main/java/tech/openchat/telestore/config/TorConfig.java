package tech.openchat.telestore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;

/**
 * @author vgorin
 * file created on 2020-04-12 16:42
 */

@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureAfter(PropertyPlaceholderAutoConfiguration.class)
public class TorConfig {
    private final Proxy tor;

    public TorConfig(@Value("${tor.host}") String host, @Value("${tor.port}") int port) {
        tor = host == null?
                new Proxy(
                        Proxy.Type.SOCKS,
                        new InetSocketAddress(port)
                ):
                new Proxy(
                        Proxy.Type.SOCKS,
                        new InetSocketAddress(host, port)
                );
    }

    @PostConstruct
    public void setupProxyConfiguration() {
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                log.trace("using tor for " + uri);
                return Collections.singletonList(tor);
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                log.warn(sa + "tor connection failed to " + uri, ioe);
            }
        });
    }
}
