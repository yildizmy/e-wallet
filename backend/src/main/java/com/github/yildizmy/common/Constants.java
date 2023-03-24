package com.github.yildizmy.common;

/**
 * Constant variables used in the project
 */
public final class Constants {

    private Constants() {
    }

    public static final String TRACE = "trace";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static final String SUCCESS = "Success";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String UNAUTHORIZED_ERROR = "Unauthorized error: {}";
    public static final String CANNOT_SET_AUTH = "Cannot set user authentication: {}";
    public static final String NOT_FOUND_USERNAME = "User with username of %1$s is not found";
    public static final String INVALID_JWT_SIGN = "Invalid JWT signature: {}";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT token: {}";
    public static final String JWT_EXPIRED = "JWT token is expired: {}";
    public static final String JWT_UNSUPPORTED = "JWT token is unsupported: {}";
    public static final String JWT_EMPTY = "JWT claims string is empty: {}";
    public static final String VALIDATION_ERROR = "Validation error. Check 'errors' field for details";
    public static final String UNKNOWN_ERROR = "Unknown error occurred";
    public static final String METHOD_ARGUMENT_NOT_VALID = "MethodArgumentNotValid exception";

    public static final String ALREADY_EXISTS = "Requested element already exists";
    public static final String ALREADY_EXISTS_USER = "User with the same username already exists";
    public static final String ALREADY_EXISTS_WALLET_IBAN = "Wallet with the same iban already exists";
    public static final String ALREADY_EXISTS_WALLET_NAME = "Wallet with the same name already exists";
    public static final String ALREADY_EXISTS_TRANSACTION = "Transaction with the same name reference number already exists";
    public static final String NOT_FOUND = "Requested element is not found";
    public static final String NOT_FOUND_RECORD = "Not found any record";
    public static final String NOT_FOUND_USER = "Requested user is not found";
    public static final String NOT_FOUND_WALLET = "Requested wallet is not found";
    public static final String NOT_FOUND_TRANSACTION = "Requested transaction is not found";
    public static final String CREATED_USER = "User is created";
    public static final String CREATED_WALLET = "Wallet is created";
    public static final String CREATED_TRANSACTION = "Type is created";
    public static final String UPDATED_WALLET = "Wallet is updated";
    public static final String DELETED_WALLET = "Wallet is deleted";
}
