package com.example.shop.constants;

public class SecurityConstants {

    public static final String AUTH_URLS = "/api/auth/**";
    public static final String ADMIN_URLS = "/api/admin/**";
    public static final String SECRET = "SecretKeyGenJWT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 600_000; //10min

    private SecurityConstants() {
    }
}
