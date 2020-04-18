package tech.openchat.telestore.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * @author vgorin
 * file created on 2020-04-14 12:53
 */

@Data
@Entity
public class ServiceArea {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String address;

}
