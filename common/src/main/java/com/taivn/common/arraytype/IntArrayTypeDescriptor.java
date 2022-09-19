package com.taivn.common.arraytype;

/**
 * The Class IntArrayTypeDescriptor.
 */
public class IntArrayTypeDescriptor extends AbstractArrayTypeDescriptor<int[]> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3493688128082921365L;

    /** The Constant SQL_TYPE_NAME. */
    private static final String SQL_TYPE_NAME = "integer";

    /** The Constant INSTANCE. */
    public static final IntArrayTypeDescriptor INSTANCE = new IntArrayTypeDescriptor();

    /**
     * Instantiates a new int array type descriptor.
     */
    public IntArrayTypeDescriptor() {
        super(int[].class);
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
}
