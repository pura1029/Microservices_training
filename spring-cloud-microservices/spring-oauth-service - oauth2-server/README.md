# Lab 1

## Steps to setup OAuth2 server 
1. Add the below dependencies to the project 
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-security</artifactId>
</dependency>
```
2. Setup the application name in `bootstrap.yaml` file 
```yaml
spring:
  application:
    name: oauthserver
``` 

3. Configure the `application.yaml` file to register with Eureka server and set the server port to 8081
```yaml
eureka:
  instance:
    prefer-ip-address: true
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      default-zone: http://localhost:8761/eureka

server:
  port: 8081
```
4. Add the below annotations in the Root Configuration file 
```
@EnableResourceServer   // this is a protected resource
@EnableAuthorizationServer  //acts as a OAuth2 service
```

5. Create a `OAuath2Config` class and extend `AuthorizationServerConfigurerAdapter`

6. Override the method to setup the `clientDetails` and `AuthorizationServrEndpoint`

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
    endpoints
            .authenticationManager(authenticationManager)
            .userDetailsService(this.userDetailsService);
}
```

7. Create configuration class and extends `WebSecurityConfigurerAdapter`

8. Override the `configure` method and setup the AuthenticationManager 

9. Configure to return both `authenticationManager` and `userDetailsService` bean

10. Once the token is generated, to validate the token, create a Controller class 
 - Add a GetMapping with `/user`
 - Accept `OAuth2Authentication` parameter 
 - Extract the `principal` and `authorities`
   ```
   Object principal = user.getUserAuthentication().getPrincipal();
   Collection<? extends GrantedAuthority> grantedAuthorities = user.getUserAuthentication().getAuthorities();
        Set<String> authorities = AuthorityUtils.authorityListToSet(grantedAuthorities);

   ```
 - Return the user details
 ```java
 Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", principal);
        userDetails.put("authorities", authorities);
        return userDetails;
 ```
11. Check the user information by making a `GET` request
```
GET http://localhost:8081/user -- headers Authorization Bearer <access_token>
```
