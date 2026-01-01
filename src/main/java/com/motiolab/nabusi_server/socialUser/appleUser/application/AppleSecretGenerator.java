package com.motiolab.nabusi_server.socialUser.appleUser.application;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class AppleSecretGenerator {
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;

    private static final String AUDIENCE = "https://appleid.apple.com";

    private String keyId = "K73M5XWDJ7";
    private String teamId = "249JP946MX";
    private String clientId = "com.motiolab.nabusi-ios";

    public String createClientSecret() {
        final ClassPathResource resource = new ClassPathResource("appleKey/AuthKey_K73M5XWDJ7.p8");

        try (InputStream inputStream = resource.getInputStream()) {
            byte[] keyBytes = inputStream.readAllBytes();
            String privateKeyPem = new String(keyBytes);
            privateKeyPem = privateKeyPem
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", ""); // Remove headers and whitespace

            final byte[] decodedKey = Base64.getDecoder().decode(privateKeyPem);
            final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            final KeyFactory keyFactory = KeyFactory.getInstance("EC");
            final PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            final long now = System.currentTimeMillis();

            return Jwts.builder()
                    .setHeader(Map.of("kid", keyId, "alg", SignatureAlgorithm.ES256))
                    .setIssuer(teamId)
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + (10 * MINUTE)))
                    .setSubject(clientId)
                    .setAudience(AUDIENCE)
                    .signWith(privateKey, SignatureAlgorithm.ES256)
                    .compact();

        } catch (Exception e) {
            throw new RuntimeException("Error generating client secret", e);
        }
    }
}
