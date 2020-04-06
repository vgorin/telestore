package tech.openchat.telestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import tech.openchat.telestore.entity.Currency;
import tech.openchat.telestore.entity.Wallet;
import tech.openchat.telestore.repository.WalletRepository;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author vgorin
 * file created on 2020-04-05 04:46
 */

@Slf4j
@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createNewWallet() {
        try {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();


        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.USDT);
        wallet.setPrivateKey(privateKeyInDec);
        return wallet;
        }
        catch(InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            log.error("error creating wallet key pair", e);
            throw new RuntimeException(e);
        }
    }
}
