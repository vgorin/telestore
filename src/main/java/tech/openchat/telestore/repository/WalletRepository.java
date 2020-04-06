package tech.openchat.telestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.openchat.telestore.entity.Wallet;

/**
 * @author vgorin
 * file created on 2020-04-05 04:55
 */

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
