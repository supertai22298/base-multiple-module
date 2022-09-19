/**

 * <p>
 * Description: The file class
 * <p>
 * Change history:
 * Date             Defect#             Person             Comments
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 */
package com.taivn.common.builder.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serializable;


@Setter

@Getter

/**
 * Instantiates a new search result.
 */
@NoArgsConstructor
public class SearchResult<T> implements Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8889916279528418133L;

    /** The items. */
    private Page<T> page;

    /** The total items. */
    private Long totalItems;
}
