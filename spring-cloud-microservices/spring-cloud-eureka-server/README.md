# Spring Cloud Eureka Server 
## Setting up Eureka Discovery Server 

1. Set up the Spring Boot project with the following configuration from [start.spring.io](https://start.spring.io) portal
   - Spring Version - 2.3.7.RELEASE
   - Java Version - 8
   - Spring Cloud Version - Hoxton.SR9

2. Add the following dependencies 
```xml
     <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
     </dependency>
```

3. Exclude the deprecated `Ribbon` dependency 
```xml
 <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </exclusion>
        <exclusion>
            <groupId>com.netflix.ribbon</groupId>
            <artifactId>ribbon-eureka</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

4. Add the `load-balancer` dependency
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```
5. Create a file called `bootstrap.yaml` under `src/main/resources` directory

6. Add the following code under the `bootstrap.yaml` file 
```yaml
spring:
  application:
    name: eureka-server
  cloud:
    config:
      uri: http://localhost:8888
    loadbalancer:
      ribbon:
        enabled: false
```
7. In the `application.yaml` file add the below code 
```yaml
server:
  port: 8761
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
  server:
    wait-time-in-ms-when-sync-empty: 5

```

8. Add the `@EnableEurekaServer` annotation in the Root file

9. Verify the url `http://localhost:8761` and you can view the Eureka Dashboard
