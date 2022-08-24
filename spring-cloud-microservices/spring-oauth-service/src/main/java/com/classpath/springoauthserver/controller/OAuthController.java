package com.classpath.springoauthserver.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class OAuthController {

    @GetMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> fetchUserDetails(OAuth2Authentication user){
        //Fetch the Username
        Object principal = user.getUserAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> grantedAuthorities = user.getUserAuthentication().getAuthorities();
        Set<String> authorities = AuthorityUtils.authorityListToSet(grantedAuthorities);

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", principal);
        userDetails.put("authorities", authorities);
        return userDetails;
    }
}