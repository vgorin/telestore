package tech.openchat.telestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.openchat.telestore.entity.Order;

/**
 * @author vgorin
 * file created on 2020-04-05 04:54
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
}
