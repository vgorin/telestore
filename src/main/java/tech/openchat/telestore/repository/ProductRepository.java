package tech.openchat.telestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.openchat.telestore.entity.Product;

/**
 * @author vgorin
 * file created on 2020-03-29 18:49
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
