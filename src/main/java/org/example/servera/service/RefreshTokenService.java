package org.example.servera.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.example.servera.entity.RefreshToken;
import org.example.servera.repository.RefreshTokenRepository;
import org.example.servera.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RefreshTokenService {
    private Long refreshTokenDurationMs = 86400000L;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String username) {
        // check if the user already has a refresh token
        Optional<RefreshToken> existingToken =
                refreshTokenRepository.findByUserId(userRepository.findByUsername(username).getId());
        if (existingToken.isPresent()) {
            // check if the existing token is still valid
            RefreshToken refreshToken = existingToken.get();
            if (refreshToken.getExpiryDate().compareTo(Instant.now()) > 0) {
                return refreshToken;
            } else {
                refreshTokenRepository.delete(refreshToken);
            }
        }

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findByUsername(username));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return null;
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
