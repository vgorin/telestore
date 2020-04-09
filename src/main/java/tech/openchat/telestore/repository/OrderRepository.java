package tech.openchat.telestore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.openchat.telestore.entity.Order;
import tech.openchat.telestore.entity.OrderState;

import java.util.List;
import java.util.Optional;

/**
 * @author vgorin
 * file created on 2020-04-05 04:54
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserIdAndId(int userId, long id);

    Page<Order> findAllByUserId(int userId, Pageable pageable);
    Page<Order> findAllByUserIdAndState(int userId, OrderState state, Pageable pageable);

    @Query("SELECT DISTINCT o.state FROM Order o WHERE o.userId = :userId")
    List<OrderState> findStates(int userId);

    List<Order> findAllByState(OrderState state);

    default List<Order> findAllUnpaid() {
        return findAllByState(OrderState.UNPAID);
    }
}
