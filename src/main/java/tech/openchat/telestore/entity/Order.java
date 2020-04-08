package tech.openchat.telestore.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @NotNull
    @Column(nullable = false)
    private Integer userId;

    @NotNull
    @Column(nullable = false)
    private Long chatId;

    @NotNull
    @ManyToOne(optional = false)
    private Product product;

    @NotNull
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;

    @Positive
    private Double qty;

    @Positive
    private Double price;

    @OneToOne(optional = false)
    private Wallet wallet;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(nullable = false, length = 15)
    private OrderState state;

}
