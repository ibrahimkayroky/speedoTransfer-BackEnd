package com.example.speedoTransfer.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RefreshTokenServiceTest {

    private RefreshTokenService refreshTokenService;
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        cacheManager = new ConcurrentMapCacheManager("refreshTokens");
        refreshTokenService = new RefreshTokenService(cacheManager);
    }

    @Test
    public void testSaveToken() {
        refreshTokenService.saveToken("testToken", 1L);

        Cache cache = cacheManager.getCache("refreshTokens");
        RefreshTokenService.TokenInfo tokenInfo = cache.get("testToken", RefreshTokenService.TokenInfo.class);

        assertEquals(1L, tokenInfo.getUserId());
        assertEquals(LocalDateTime.now().plusDays(7).toLocalDate(), tokenInfo.getExpiryDate().toLocalDate());
    }

    @Test
    public void testGetTokenInfo() {
        refreshTokenService.saveToken("testToken", 1L);

        RefreshTokenService.TokenInfo tokenInfo = refreshTokenService.getTokenInfo("testToken");

        assertEquals(1L, tokenInfo.getUserId());
    }

    @Test
    public void testDeleteToken() {
        refreshTokenService.saveToken("testToken", 1L);
        refreshTokenService.deleteToken("testToken");

        RefreshTokenService.TokenInfo tokenInfo = refreshTokenService.getTokenInfo("testToken");

        assertNull(tokenInfo);
    }
}
