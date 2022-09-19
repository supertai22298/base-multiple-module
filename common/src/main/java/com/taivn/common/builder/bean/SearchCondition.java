package com.taivn.common.builder.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tai VN
 * @date 9/19/2022
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchCondition implements Serializable {

    private static final long serialVersionUID = -9054989586832315076L;


    /**
     * The conditions.
     */
    private List<Condition> conditions = new ArrayList<>();

    /**
     * The page.
     */
    private Integer page;

    /**
     * The size.
     */
    private Integer size;

    /**
     * The sort name.
     */
    private String sortName;

    /**
     * The sort direction.
     */
    private Boolean sortDirection;
}
