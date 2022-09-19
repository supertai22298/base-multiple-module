package com.taivn.common.arraytype;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * The Class StringArrayType.
 */
public class StringArrayType extends AbstractSingleColumnStandardBasicType<String[]>
        implements DynamicParameterizedType {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2339150554843876975L;

    /** The Constant STRING_TYPE. */
    private static final String STRING_TYPE = "stringArray";

    /**
     * Instantiates a new string array type.
     */
    public StringArrayType() {
        super(ArraySqlTypeDescriptor.INSTANCE, StringArrayTypeDescriptor.INSTANCE);
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return STRING_TYPE;
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
        ((StringArrayTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
