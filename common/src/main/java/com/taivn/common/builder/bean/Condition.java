package com.taivn.common.builder.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Tai VN
 * @date 9/19/2022
 */
@Getter
@Setter
@NoArgsConstructor
public class Condition implements Serializable {

    private static final long serialVersionUID = 1422259776719617644L;

    /**
     * The search key.
     */
    private String key;

    /**
     * The search value of key.
     */
    private transient Object value;

    /**
     * The search type, like eq, ge, gt, le, lt.....
     */
    private String operator;

    /**
     * The data type (string, number, date).
     */
    private String type;

    /**
     * The join table.
     */
    private String joinTable;

    /**
     * The join type.
     */
    private String joinType;
}
