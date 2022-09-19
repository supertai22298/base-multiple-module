package com.taivn.common.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Tai VN
 * @date 9/18/2022
 */
public abstract class BaseDTO<TId> extends SerializableDTO {

    private static final long serialVersionUID = 3522547489203653734L;

    @JsonIgnore
    public abstract TId getIdentifier();

    @JsonIgnore
    public abstract void setIdentifier();
}
