package ru.domru.logger.kafka.receiver.controller.token;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class Token {
    @SuppressWarnings("SpellCheckingInspection")
    private static final byte[] TOKEN_HASH_KEY = Base64.getDecoder().decode(
            "jHMh5ad4zqeGGuncxC1jH91ehKXz/DyP8e0AUemdwzbZzGTFGz0uFm742Fx40ryqck2Fpiut9gAH02XKF+JznPnalNPJtqC6Nw6KdtCn+JcaZHos0JjFRAYPYEayEQTNswxRtaiiOJBe9hzirHmOIddQDW6lMvStkTj3VZxcAnc=".getBytes());

    private final String tokenString;
    private final ObjectMapper mapper;
    private String data;
    private String signature;
    private TokenData tokenData = new TokenData();

    public Token(String tokenString, ObjectMapper mapper) {
        this.tokenString = tokenString;
        this.mapper = mapper;
    }

    public String serialize(String data) throws IOException {
        this.data = data;
        this.signature = generateSignature(this.data);
        return new String(Base64.getEncoder().encode(mapper.writeValueAsBytes(this)));
    }

    public void deserialize() throws IOException {
        byte[] tokenBytes = Base64.getDecoder().decode(tokenString.getBytes());
        mapper.readerForUpdating(this).readValue(tokenBytes);
        mapper.readerForUpdating(tokenData).readValue(data.getBytes());
    }

    boolean isSignatureValid() {
        return generateSignature(data).equals(signature);
    }

    private String generateSignature(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(TOKEN_HASH_KEY, "HmacSHA256"));
            byte[] dataSignatureBytes = Base64.getEncoder().encode(mac.doFinal(data.getBytes()));
            return new String(dataSignatureBytes);
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public TokenPrincipal getPrincipal() {
        return tokenData.getPrincipal();
    }

    public String getData() {
        return data;
    }

    public String getSignature() {
        return signature;
    }

    public String toString() {
        return tokenString;
    }

    private static class TokenData {
        private Long expires;
        private Long lifespan;
        private TokenPrincipal principal;

        public Long getExpires() {
            return expires;
        }

        public Long getLifespan() {
            return lifespan;
        }

        public TokenPrincipal getPrincipal() {
            return principal;
        }
    }
}
