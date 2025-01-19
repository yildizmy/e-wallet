package com.github.yildizmy.common;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

/**
 * Utility class to hold constant values used throughout the application.
 */
@UtilityClass
public class Constants {

    public static final String ALLOWED_ORIGIN = "http://localhost:3000";
    public static final String TRACE = "trace";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final int IBAN_MIN_SIZE = 15;
    public static final int IBAN_MAX_SIZE = 34;
    public static final long IBAN_MAX = 999999999;
    public static final long IBAN_MODULUS = 97;
}
