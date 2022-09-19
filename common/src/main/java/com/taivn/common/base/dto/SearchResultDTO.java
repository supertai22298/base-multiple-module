package com.taivn.common.base.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tai VN
 * @date 9/18/2022
 */
@Getter
@Setter
@NoArgsConstructor

public class SearchResultDTO<T extends SerializableDTO> implements Serializable {

    private static final long serialVersionUID = 321982203490912802L;

    private List<T> items;

    private Long totalItems;
}
