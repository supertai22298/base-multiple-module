package com.taivn.common.arraytype;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * The Class AbstractArrayTypeDescriptor.
 *
 * @param <T> the generic type
 */
public abstract class AbstractArrayTypeDescriptor<T> extends AbstractTypeDescriptor<T>
        implements DynamicParameterizedType {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The array object class. */
    private Class<T> arrayObjectClass;

    /**
     * Sets the parameter values.
     *
     * @param parameters the new parameter values
     */
    @Override
    public void setParameterValues(Properties parameters) {
        arrayObjectClass = ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();
    }

    /**
     * Instantiates a new abstract array type descriptor.
     *
     * @param arrayObjectClass the array object class
     */
    public AbstractArrayTypeDescriptor(Class<T> arrayObjectClass) {
        super(arrayObjectClass, (MutabilityPlan<T>) new MutableMutabilityPlan<Object>() {
            @Override
            protected T deepCopyNotNull(Object value) {
                return ArrayUtil.deepCopy(value);
            }
        });
        this.arrayObjectClass = arrayObjectClass;
    }

    /**
     * Are equal.
     *
     * @param one     the one
     * @param another the another
     * @return true, if successful
     */
    @Override
    public boolean areEqual(Object one, Object another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }
        return ArrayUtil.isEquals(one, another);
    }

    /**
     * To string.
     *
     * @param value the value
     * @return the string
     */
    @Override
    public String toString(Object value) {
        return Arrays.deepToString((Object[]) value);
    }

    /**
     * From string.
     *
     * @param string the string
     * @return the t
     */
    @Override
    public T fromString(String string) {
        return ArrayUtil.fromString(string, arrayObjectClass);
    }

    /**
     * Unwrap.
     *
     * @param <X>     the generic type
     * @param value   the value
     * @param type    the type
     * @param options the options
     * @return the x
     */
    @SuppressWarnings({ "unchecked" })
    @Override
    public <X> X unwrap(T value, Class<X> type, WrapperOptions options) {
        return (X) ArrayUtil.wrapArray(value);
    }

    /**
     * Wrap.
     *
     * @param <X>     the generic type
     * @param value   the value
     * @param options the options
     * @return the t
     */
    @Override
    public <X> T wrap(X value, WrapperOptions options) {
        if (value instanceof Array) {
            Array array = (Array) value;
            try {
                return ArrayUtil.unwrapArray((Object[]) array.getArray(), arrayObjectClass);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return (T) value;
    }

    /**
     * Gets the sql array type.
     *
     * @return the sql array type
     */
    protected abstract String getSqlArrayType();
}
