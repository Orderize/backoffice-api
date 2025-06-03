package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.PasswordResetToken;
import com.orderize.backoffice_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(User user);
}
