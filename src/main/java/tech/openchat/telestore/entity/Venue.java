package tech.openchat.telestore.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String address;

    private String description;

    private Picture picture;
    @NotNull
    @Column(nullable = false)
    private Double locLat;

    @NotNull
    @Column(nullable = false)
    private Double locLon;
}
