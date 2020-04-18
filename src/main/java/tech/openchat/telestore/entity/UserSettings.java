package tech.openchat.telestore.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author vgorin
 * file created on 2020-04-14 12:37
 */

@Data
@Entity
public class UserSettings {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer userId;

    @ManyToOne
    private ServiceArea serviceArea;

}
