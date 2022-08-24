# Setting up JWT token

1. Self contained
2. Extensible
3. Cryptographycally secure

## Steps to setup JWT 
1. Add the dependencies
```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-jwt</artifactId>
    <version>1.1.0.RELEASE</version>
</dependency>
```

2. Create a JWTOAuth2Config which extends `AuthorizationServerConfigurerAdapter` class and override the configure method and set up client id and client secret
```java 
 @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("my-awesome-app")
                .secret("{noop}welcome")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("webclient", "mobile");
    }

 @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer));
        
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .userDetailsService(this.userDetailsService);
    }
```

3. Create the configuration class to setup the JWT token creation and management

```java
@Configuration
public class JWTTokenStoreConfiguration {

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("welcometojwttoken");
        return jwtAccessTokenConverter;
    }

}
```

5. Create a custom `JWTTokenEnhancer` class which implements `TokenEnhancer` interface and override the `enhance` method

```java
 @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> details = new HashMap<>();
        details.put("username", "pradeep");
        ((DefaultOAuth2AccessToken)(accessToken)).setAdditionalInformation(details);
        return accessToken;

    }
```
