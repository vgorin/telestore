package tech.openchat.telestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.openchat.telestore.entity.Order;
import tech.openchat.telestore.entity.OrderState;
import tech.openchat.telestore.repository.OrderRepository;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author vgorin
 * file created on 2020-04-08 19:24
 */

@Slf4j
@Component
public class Web3Daemon {
    private final TetherService tetherService;
    private final OrderRepository orderRepository;

    public Web3Daemon(TetherService tetherService, OrderRepository orderRepository) {
        this.tetherService = tetherService;
        this.orderRepository = orderRepository;
    }

    @Scheduled(fixedRate = 14247)
    public void updateWalletBalances() {
        for(Order unpaidOrder: orderRepository.findAllUnpaid()) {
            try {
                BigDecimal newBalance = tetherService.getBalance(unpaidOrder.getWallet().getAddress());

                if(newBalance.compareTo(BigDecimal.valueOf(unpaidOrder.getPrice())) >= 0) {
                    unpaidOrder.setState(OrderState.PAID);
                    orderRepository.save(unpaidOrder);
                }
            }
            catch(IOException e) {
                log.debug("getBalance", e);
            }
        }
    }
}
