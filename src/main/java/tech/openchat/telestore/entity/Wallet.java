package tech.openchat.telestore.entity;

import lombok.Data;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @author vgorin
 * file created on 2020-04-05 02:04
 */

@Data
@Entity
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private Currency currency;

    @NotNull
    @Column(nullable = false)
    private BigInteger privateKey;

    public BigInteger getPublicKey() {
        return ECKeyPair.create(privateKey).getPublicKey();
    }

    public String getAddressHex() {
        return Keys.getAddress(ECKeyPair.create(privateKey));
    }
}
