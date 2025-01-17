package com.github.yildizmy.common;

/**
 * Constant variables used in the project. A private constructor is added to prevent instantiation.
 */
public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException();
    }

    public static final String TRACE = "trace";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final int IBAN_MIN_SIZE = 15;
    public static final int IBAN_MAX_SIZE = 34;
    public static final long IBAN_MAX = 999999999;
    public static final long IBAN_MODULUS = 97;
}
