package org.example.servera.repository;

import java.util.Optional;

import org.example.servera.entity.RefreshToken;
import org.example.servera.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserId(Integer userid);
    @Modifying
    int deleteByUser(User user);
}
