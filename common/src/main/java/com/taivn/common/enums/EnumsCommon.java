/*
 * (C) Copyright Hitachi Vantara (HVN) 2021. All rights reserved. Proprietary and confidential.
 */
package com.taivn.common.enums;

/**
 * The enum common.
 *
 * @author Taivn
 */
public class EnumsCommon {

    /**
     * Instantiates a new enum.
     */
    public static class Enum {

        /**
         * Enum for status parameter.
         *
         * @author Taivn
         */
        public static enum Status {
            DRAFT("Draft"), ACTIVE("Active"), INACTIVE("Inactive"), DISABLED("Disabled");

            private final String value;

            /**
             * Instantiates a status enum.
             *
             * @param value value
             */
            Status(String value) {
                this.value = value;
            }

            /**
             * Get value enum.
             *
             * @return value
             */
            public String getValue() {
                return value;
            }
        }
    }

    /**
     * Instantiates a new enums common.
     */
    private EnumsCommon() {
        throw new IllegalStateException("EnumsCommon class");
    }

}
