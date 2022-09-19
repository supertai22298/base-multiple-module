package com.taivn.common.base.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The class BaseEntity
 *
 * @author Tai VN
 * @date 9/18/2022
 */
@Getter
@Setter

/**
 *  Instantiates a new base entity.
 */
@NoArgsConstructor
public abstract class BaseEntity<TId> implements Serializable {

    private static final long serialVersionUID = 3927981449952936338L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private TId id;
}
