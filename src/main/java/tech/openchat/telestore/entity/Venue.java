package tech.openchat.telestore.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author vgorin
 * file created on 2020-04-05 02:20
 */

@Data
@Entity
public class Venue {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String address;

    private String description;

    @OneToOne
    private Picture picture;

    @NotNull
    @Column(nullable = false)
    private Double locLat;

    @NotNull
    @Column(nullable = false)
    private Double locLon;

}
