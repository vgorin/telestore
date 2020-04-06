package tech.openchat.telestore.service;

import org.springframework.stereotype.Service;
import tech.openchat.telestore.repository.OrderRepository;

/**
 * @author vgorin
 * file created on 2020-04-05 04:42
 */

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
