package edu.cornell.PPMFullStack.security;

public class SecurityConstants {
    public static final String SIGN_UP_URL_STRING= "/api/users/**";
    public static final String SECRET= "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING= "Authorization";
    public static final Long EXPIRATION_TIME= (long) 30_000;
}
