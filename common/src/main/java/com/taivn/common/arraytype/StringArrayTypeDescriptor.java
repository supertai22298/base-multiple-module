package com.taivn.common.arraytype;

import org.hibernate.type.descriptor.WrapperOptions;
import org.springframework.util.StringUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class StringArrayTypeDescriptor.
 */
public class StringArrayTypeDescriptor extends AbstractArrayTypeDescriptor<String[]> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant SQL_TYPE_NAME. */
    private static final String SQL_TYPE_NAME = "text";

    /** The Constant DELIMITER. */
    private static final char DELIMITER = ',';

    /** The Constant PREFIX_SLASH. */
    private static final char PREFIX_SLASH = '\\';

    /** The Constant DOUBLE_COMMA. */
    private static final char DOUBLE_COMMA = '"';

    /** The Constant OPEN_ARRAY. */
    private static final char OPEN_ARRAY = '{';

    /** The Constant CLOSE_ARRAY. */
    private static final char CLOSE_ARRAY = '}';

    /** The Constant FULL_SIZE_SPACE. */
    private static final char FULL_SIZE_SPACE = '　';

    /** The Constant EQUAL. */
    private static final char EQUAL = '=';

    /** The Constant START_EQUAL. */
    private static final char START_EQUAL = '[';

    /** The Constant NULL. */
    private static final String NULL = "NULL";

    /** The Constant INSTANCE. */
    public static final StringArrayTypeDescriptor INSTANCE = new StringArrayTypeDescriptor();

    /**
     * Instantiates a new string array type descriptor.
     */
    public StringArrayTypeDescriptor() {
        super(String[].class);
    }

    /**
     * Gets the sql array type.
     *
     * @return the sql array type
     */
    @Override
    protected String getSqlArrayType() {
        return SQL_TYPE_NAME;
    }

    /**
     * Wrap array.
     *
     * @param <X>     the generic type
     * @param value   the value
     * @param options the options
     * @return the string[]
     */
    @Override
    public <X> String[] wrap(X value, WrapperOptions options) {
        if (value instanceof Array) {
            Array array = (Array) value;
            try {
                return this.buildArray((array));
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return (String[]) value;
    }

    /**
     * Build array.
     *
     * @param array the array
     * @return the string[]
     */
    private synchronized String[] buildArray(Array array) {
        if (array == null) {
            return null;
        }
        String fieldData = array.toString();
        if (StringUtils.isEmpty(fieldData)) {
            return null;
        }
        return this.convertToArray(fieldData);
    }

    /**
     * Convert to array data.
     *
     * @param stringData the string data
     * @return the string[]
     */
    private String[] convertToArray(String stringData) {

        ArrayObject arrayList = new ArrayObject();
        char[] chars = stringData.toCharArray();
        StringBuilder buffer = null;
        boolean insideString = false;
        boolean wasInsideString = false;
        List<ArrayObject> dims = new ArrayList();
        ArrayObject curArray = arrayList;
        int startOffset = 0;
        if (chars[0] == START_EQUAL) {
            while (chars[startOffset] != EQUAL) {
                ++startOffset;
            }
            ++startOffset;
        }

        for (int i = startOffset; i < chars.length; ++i) {
            if (chars[i] == PREFIX_SLASH) {
                ++i;
            } else {
                if (!insideString && chars[i] == OPEN_ARRAY) {
                    if (dims.isEmpty()) {
                        dims.add(arrayList);
                    } else {
                        ArrayObject a = new ArrayObject();
                        ArrayObject p = dims.get(dims.size() - 1);
                        p.add(a);
                        dims.add(a);
                    }

                    curArray = dims.get(dims.size() - 1);
                    for (int t = i + 1; t < chars.length; ++t) {
                        if (!Character.isWhitespace(chars[t])) {
                            if (chars[t] != OPEN_ARRAY) {
                                break;
                            }
                            ++curArray.dimensionsCount;
                        }
                    }
                    buffer = new StringBuilder();
                    continue;
                }

                if (chars[i] == DOUBLE_COMMA) {
                    insideString = !insideString;
                    wasInsideString = true;
                    continue;
                }

                if (!insideString && Character.isWhitespace(chars[i]) && chars[i] != FULL_SIZE_SPACE) {
                    continue;
                }

                if (!insideString && (chars[i] == DELIMITER || chars[i] == CLOSE_ARRAY) || i == chars.length - 1) {
                    if (chars[i] != DOUBLE_COMMA && chars[i] != CLOSE_ARRAY && chars[i] != DELIMITER
                            && buffer != null) {
                        buffer.append(chars[i]);
                    }

                    String b = buffer == null ? null : buffer.toString();
                    if (b != null && (!b.isEmpty() || wasInsideString)) {
                        curArray.add(!wasInsideString && b.equals(NULL) ? null : b);
                    }

                    wasInsideString = false;
                    buffer = new StringBuilder();
                    if (chars[i] == CLOSE_ARRAY) {
                        dims.remove(dims.size() - 1);
                        if (!dims.isEmpty()) {
                            curArray = dims.get(dims.size() - 1);
                        }

                        buffer = null;
                    }
                    continue;
                }
            }
            if (buffer != null) {
                buffer.append(chars[i]);
            }
        }
        return arrayList.toArray(new String[0]);
    }

    /**
     * Array object.
     */
    private static class ArrayObject extends ArrayList<Object> {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 2052781232100062677L;

        /** The dimensions count. */
        int dimensionsCount;

        /**
         * Instantiates a new array object.
         */
        private ArrayObject() {
            this.dimensionsCount = 1;
        }
    }
}
