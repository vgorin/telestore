package tech.openchat.telestore.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author vgorin
 * file created on 2020-04-04 23:41
 */

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @NotNull
    @Column(nullable = false)
    private Date date;

    @Positive
    private Double qty;

    @Positive
    private Double price;

    @OneToOne(optional = false)
    private Wallet wallet;


}
