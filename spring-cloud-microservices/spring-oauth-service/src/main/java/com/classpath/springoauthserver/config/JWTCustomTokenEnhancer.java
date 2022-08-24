package com.classpath.springoauthserver.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public class JWTCustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> details = new HashMap<>();
        details.put("username", "pradeep");
        ((DefaultOAuth2AccessToken)(accessToken)).setAdditionalInformation(details);
        return accessToken;

    }

}