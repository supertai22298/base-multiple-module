package com.taivn.common.arraytype;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * The Class IntArrayType.
 */
public class IntArrayType extends AbstractSingleColumnStandardBasicType<int[]> implements DynamicParameterizedType {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7879603789842898595L;

    /** The Constant INT_TYPE. */
    private static final String INT_TYPE = "intArray";

    /**
     * Instantiates a new int array type.
     */
    public IntArrayType() {
        super(ArraySqlTypeDescriptor.INSTANCE, IntArrayTypeDescriptor.INSTANCE);
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return INT_TYPE;
    }

    /**
     * Register under java type.
     *
     * @return true, if successful
     */
    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    /**
     * Sets the parameter values.
     *
     * @param parameters the new parameter values
     */
    @Override
    public void setParameterValues(Properties parameters) {
        ((IntArrayTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
