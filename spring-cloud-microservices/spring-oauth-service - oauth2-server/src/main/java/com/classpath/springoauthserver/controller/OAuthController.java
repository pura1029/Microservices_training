package com.classpath.springoauthserver.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class OAuthController {

    @GetMapping
    public Map<String,Object> userDetails(OAuth2Authentication auth2Authentication){
        Object principal = auth2Authentication.getUserAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> grantedAuthorities = auth2Authentication.getUserAuthentication().getAuthorities();
        Set<String> authorities = AuthorityUtils.authorityListToSet(grantedAuthorities);
        Map<String,Object> map = new HashMap<>();
        map.put("user", principal);
        map.put("authorities", authorities);
        return map;
    }
    
}
