package com.orderize.backoffice_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.orderize.backoffice_api.model.User;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenService {
    private final String secret;
    private final Algorithm algorithm;

    public TokenService() {
        
        this.secret = System.getenv("TOKEN_SECRET");

        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generationToken(User user) {
        try {
            String token = JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getEmail())
                    .withClaim("userId", user.getId())
                    .withClaim("name", user.getName())
                    .withExpiresAt(getExpirationTokenDate())
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Erro na criação do token JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Erro na verificação do token JWT");
        }
    }

    public Long getUserIdFromToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getClaim("userId").asLong();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar identificado do usuário", e);
        }
    }

    // Atualmente deixei o token com 7 dias de duração até expirar, não parece muito safe my friend but,
    // sla podemos mudar depois ta de graça.
    // Talvez não faça sentido essa duração, não sei como uma duração tão longa pode afetar o front (ainda)
    private Instant getExpirationTokenDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }
}
