package com.mx.axeleratum.americantower.contract.admin.security;

public class SecurityConstants {
    public static final String SECRET = "SecretAmericanTowerKey";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SWAGGER_INTERNAL_DOC_URL = "/v2/api-docs";
}
