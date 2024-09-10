package com.example.speedoTransfer.security.service;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class RefreshTokenService implements Serializable {

    private final CacheManager cacheManager;

    public RefreshTokenService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @CachePut(value = "refreshTokens", key = "#token")
    public void saveToken(String token, Long userId) {
        Objects.requireNonNull(cacheManager.getCache("refreshTokens")).put(token, new TokenInfo(userId, LocalDateTime.now().plusDays(7)));
    }

    @Cacheable(value = "refreshTokens", key = "#token")
    public TokenInfo getTokenInfo(String token) {
        return (TokenInfo) cacheManager.getCache("refreshTokens").get(token).get();
    }

    @CacheEvict(value = "refreshTokens", key = "#token")
    public void deleteToken(String token) {
    }

    public static class TokenInfo {
        private Long userId;
        private LocalDateTime expiryDate;

        public TokenInfo(Long userId, LocalDateTime expiryDate) {
            this.userId = userId;
            this.expiryDate = expiryDate;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public LocalDateTime getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(LocalDateTime expiryDate) {
            this.expiryDate = expiryDate;
        }
    }
}
