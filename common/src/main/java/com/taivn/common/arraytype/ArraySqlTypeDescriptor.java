package com.taivn.common.arraytype;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.*;

/**
 * The Class ArraySqlTypeDescriptor.
 */
public class ArraySqlTypeDescriptor implements SqlTypeDescriptor {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6639082850933830376L;
    /** The Constant INSTANCE. */
    public static final ArraySqlTypeDescriptor INSTANCE = new ArraySqlTypeDescriptor();

    /**
     * Gets the sql type.
     *
     * @return the sql type
     */
    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    /**
     * Can be remapped.
     *
     * @return true, if successful
     */
    @Override
    public boolean canBeRemapped() {
        return true;
    }

    /**
     * Gets the binder.
     *
     * @param <X>                the generic type
     * @param javaTypeDescriptor the java type descriptor
     * @return the binder
     */
    @Override
    public <X> ValueBinder<X> getBinder(JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
                    throws SQLException {
                AbstractArrayTypeDescriptor<Object> abstractArrayTypeDescriptor = (AbstractArrayTypeDescriptor<Object>) javaTypeDescriptor;
                st.setArray(index, st.getConnection().createArrayOf(abstractArrayTypeDescriptor.getSqlArrayType(),
                        abstractArrayTypeDescriptor.unwrap(value, Object[].class, options)));
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                throw new UnsupportedOperationException("Binding by name is not supported!");
            }
        };
    }

    /**
     * Gets the extractor.
     *
     * @param <X>                the generic type
     * @param javaTypeDescriptor the java type descriptor
     * @return the extractor
     */
    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>(javaTypeDescriptor, this) {
            @Override
            protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(rs.getArray(name), options);
            }

            @Override
            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(statement.getArray(index), options);
            }

            @Override
            protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
                    throws SQLException {
                return javaTypeDescriptor.wrap(statement.getArray(name), options);
            }
        };
    }
}
