package tech.openchat.telestore.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author vgorin
 * file created on 2020-03-29 15:04
 */

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Lob
    @Column(length = 0x1000) // 4kb
    private String description;

    @Positive
    private Double qty;

    @Positive
    private Double price;
}
