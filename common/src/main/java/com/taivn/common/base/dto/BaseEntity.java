package com.taivn.common.base.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The class BaseEntity
 *
 * @author Tai VN
 * @date 9/18/2022
 */
@Getter
@Setter
@MappedSuperclass
/**
 *  Instantiates a new base entity.
 */
@NoArgsConstructor
public abstract class BaseEntity<TId> implements Serializable {

    private static final long serialVersionUID = 3927981449952936338L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private TId id;
}
