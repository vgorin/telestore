package tech.openchat.telestore.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author vgorin
 * file created on 2020-04-06 12:38
 */

@Data
@Entity
public class Picture {
    @Id
    @GeneratedValue
    private Long id;

    private String url;
}
