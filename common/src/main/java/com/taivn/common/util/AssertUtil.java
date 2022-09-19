/**
 *
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 *                                                                                
 */
package com.taivn.common.util;

import com.taivn.common.enums.ErrorCodes;
import com.taivn.common.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Instantiates a new assert.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssertUtil {

    /**
     * Not null.
     *
     * @param <TId>  the generic type
     * @param object the object
     * @param id     the id
     * @throws NotFoundException the jars not found exception
     */
    public static <TId> void notNull(Object object, TId id) {
        if (object == null) {
            String message = String.format("Not found item with specified ID=[%s]", id.toString());
            throw new NotFoundException(ErrorCodes.NOT_FOUND, message);
        }
    }
}
