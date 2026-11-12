package com.example.bancario.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bancario.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenConfig {
    private String secret = "secret";

    public String generateToken(Cliente cliente){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("ID cliente", cliente.getId())
                .withSubject(cliente.getLogin())
                .withExpiresAt(Instant.now().plusSeconds(90444))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTClienteData> validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build().verify(token);

            return Optional.of(JWTClienteData.builder().id(decodedJWT.getClaim("Id cliente").asLong())
                            .login(decodedJWT.getSubject()).build());
        }catch (JWTVerificationException e){
            return Optional.empty();
        }

    }
}
