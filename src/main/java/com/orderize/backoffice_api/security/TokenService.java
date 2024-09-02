package com.orderize.backoffice_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.orderize.backoffice_api.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    // Lembrar de esconder essa parada, não pode ficar no github não, mas a essa altura do campeonato acho que não
    // pega nada
    private final String secret = "orderize_super_confidential";

    public String generationToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getEmail())
                    .withExpiresAt(getExpirationTokenDate())
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Erro na criação do token JWT");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Erro na verificação do token JWT");
        }
    }

    // Atualmente deixei o token com 7 dias de duração até expirar, não parece muito safe my friend but,
    // sla podemos mudar depois ta de graça.
    // Talvez não faça sentido essa duração, não sei como uma duração tão longa pode afetar o front (ainda)
    private Instant getExpirationTokenDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("-03:00"));
    }
}
