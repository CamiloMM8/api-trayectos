package com.example.Proyecto.Config.Model;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class Constants {

    public static final String LOGIN_URL = "/authenticate";
    public static final String PUBLIC_URL = "/ProyectoV1/public/**";
    public static final String HEADER_AUTH = "Authorization";
    public static final String HEADER_API_KEY = "apikey";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    public static final String TIPO_PERSONA_ADMIN = "A";
    public static final String TIPO_PERSONA_CONDUCTOR = "C";

    public static final String SUPER_SECRET_KEY = "ProyectoPPOOIIJWTSecretKey2026SeguroParaHS512";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000;

    public static Key getSigningKeyB64(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Key getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 64) {
            keyBytes = sha512(keyBytes);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static byte[] sha512(byte[] input) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 no disponible", e);
        }
    }
}
