package tech.openchat.telestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import tech.openchat.telestore.entity.*;
import tech.openchat.telestore.repository.OrderRepository;
import tech.openchat.telestore.repository.WalletRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

/**
 * @author vgorin
 * file created on 2020-04-05 04:42
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public Order placeOrder(int userId, long chatId, Product product) {
        Order order = new Order();
        order.setUserId(userId);
        order.setChatId(chatId);
        order.setProduct(product);
        order.setPrice(product.getPrice());
        order.setWallet(createNewWallet());
        order.setState(OrderState.UNPAID);
        return orderRepository.save(order);
    }

    public Order getOrder(long id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Order getOrder(int userId, long orderId) {
        return orderRepository.findByUserIdAndId(userId, orderId).orElseThrow(EntityNotFoundException::new);
    }

    public Page<Order> listOrders(int userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable);
    }

    public Page<Order> listOrders(int userId, OrderState state, Pageable pageable) {
        if(state == null) {
            return listOrders(userId, pageable);
        }

        return orderRepository.findAllByUserIdAndState(userId, state, pageable);
    }

    public List<OrderState> listOrderStates(int userId) {
        return orderRepository.findStates(userId);
    }

    private Wallet createNewWallet() {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

            Wallet wallet = new Wallet();
            wallet.setCurrency(Currency.USDT);
            wallet.setPrivateKey(privateKeyInDec);
            return walletRepository.save(wallet);
        }
        catch(InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            log.error("error creating wallet key pair", e);
            throw new RuntimeException(e);
        }
    }
}
