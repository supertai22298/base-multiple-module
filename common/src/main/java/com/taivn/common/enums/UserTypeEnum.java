/**

 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Sep 1, 2021     ********           Administrator            Initialize                  
 *                                                                                
 */
package com.taivn.common.enums;

/**
 * The Enum UserType.
 *
 * @author Taivn
 */
public enum UserTypeEnum {

    /** The admin. */
    ADMIN(1),

    /** The Normal user. */
    NORMAL(2);

    /** The value. */
    private final int value;

    /**
     * Instantiates a new user type.
     *
     * @param value the value
     */
    private UserTypeEnum(int value) {
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }
}
