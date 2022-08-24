package com.classpath.springoauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer   // this is a protected resource
@EnableAuthorizationServer  //acts as a OAuth2 service
public class SpringOauthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOauthServerApplication.class, args);
	}

}
