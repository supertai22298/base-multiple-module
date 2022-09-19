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

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * The Class RandomString.
 */
public class RandomStringUtil {

    /** The Constant DEFAULT_LENGTH. */
    private static final int DEFAULT_LENGTH = 21;

    /** The Constant UPPER. */
    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** The Constant LOWER. */
    public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

    /** The Constant DIGITS. */
    public static final String DIGITS = "0123456789";

    /** The Constant ALPHANUM. */
    public static final String ALPHANUM = UPPER + LOWER + DIGITS;

    /** The random. */
    private final Random random;

    /** The symbols. */
    private final char[] symbols;

    /** The buf. */
    private final char[] buf;

    /**
     * Instantiates a new random string.
     *
     * @param length  the length
     * @param random  the random
     * @param symbols the symbols
     */
    public RandomStringUtil(int length, Random random, String symbols) {
        if (length < 1) {
            throw new IllegalArgumentException();
        }

        final int SYMBOL_LENGTH = 2;
        if (symbols.length() < SYMBOL_LENGTH) {
            throw new IllegalArgumentException();
        }

        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Instantiates a new random string.
     *
     * @param length the length
     * @param random the random
     */
    public RandomStringUtil(int length, Random random) {
        this(length, random, ALPHANUM);
    }

    /**
     * Instantiates a new random string.
     *
     * @param length the length
     */
    public RandomStringUtil(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Instantiates a new random string.
     */
    public RandomStringUtil() {
        this(DEFAULT_LENGTH);
    }

    /**
     * Generate a random string.
     *
     * @return the string
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }

        return new String(buf);
    }
}
