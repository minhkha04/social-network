package com.minhkha.identity.config;

import com.minhkha.identity.entity.User;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${app.jwt.secret}")
    private String SIGNER_KEY;

    @Value("${app.jwt.expiration_ms}")
    private long EXPIRATION_MS;

    public String generateToken(User user)  {
        try {
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
            // Create JWT claims set
            JWTClaimsSet jwsClaimSet = new JWTClaimsSet.Builder()
                    .subject(user.getId())
                    .issuer("techleaf.pro")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                    .claim("scope", user.getRole())
                    .jwtID(UUID.randomUUID().toString())
                    .build();
            Payload payload = new Payload(jwsClaimSet.toJSONObject());
            // Create JWS object
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            // Sign the JWS object
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException();
        }

    }

    public SignedJWT verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean verified = signedJWT.verify(new MACVerifier(SIGNER_KEY.getBytes()));
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean isValid = verified && expirationTime.after(new Date());
            if (!isValid) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
            return signedJWT;
        } catch (JOSEException | ParseException e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }
}
