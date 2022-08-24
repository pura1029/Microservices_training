# Spring Cloud Config Server - Lab

1. Set up the Spring Boot project with the following configuration from [start.spring.io](https://start.spring.io) portal
   - Spring Version - 2.3.7.RELEASE
   - Java Version - 8
   - Spring Cloud Version - Hoxton.SR9

2. Add the following dependencies 
```xml
     <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
```
3. Create a file called `bootstrap.yaml` under `src/main/resources` directory

4. Add the following code under the `bootstrap.yaml` file 
```yaml
spring:
  application:
    name: configserver

```
5. Create a directory called `config` under `src/main/resources` directory

6. Create two directories called `inventoryservice` and `orderservice` under the `src/main/resources/config` directory

7. In the `inventoryservice` and `orderservice` directories create three files namely
    - `inventoryservice.properties`
    - `inventoryservice-dev.properties`
    - `inventoryservice-qa.properties`
    - `orderservice.properties`
    - `orderservice-dev.properties`
    - `orderservice-qa.properties`

8. In the `application.yaml` file add the below configuration

```yaml
spring:
  profiles:
    # native is the way to define the location of file system
    active: native
  cloud:
    config:
      server:
        native:
          # search-locations: file:///E:/config/inventoryservice
          # configuring the inventoryservice and orderservice
           search-locations: classpath:/config/inventoryservice, classpath:/config/orderservice
#server port for the spring cloud config server
server:
  port: 8888
```

9. Add the `@EnableConfigServer` annotation on the main class

10. Start the server and verify the configuration for `inventoryservice` and `orderservice` for `default`, `dev` and `qa` profile 
  - URL to verify the `inventoryservice` configuraion - `http://localhost:8888/inventoryservice/dev`
  - URL to verify the `inventoryservice` configuraion - `http://localhost:8888/orderservice/dev` 


### Lab 2 - Private Git repo
1. Create a branch from `2.0.0-private-git-repo-start` tag  
2. Open the `application.yaml` file
3. Configure the `spring.profiles.active` to `git`
4. Configure the git repo for `orderservice`
```yaml
cloud:
    config:
      server:
        git:
          uri: git@gitlab.com:classpath-spring-microservices/orderservice.git

```
5. Configure the `inventoryservice` git repo
```yaml
spring:
  profiles:
    active: git
  cloud:
    config:
      server:
        git:
          uri: git@gitlab.com:classpath-spring-microservices/orderservice.git
          repos:
            inventoryservice:
              uri: git@gitlab.com:classpath-spring-microservices/inventoryservice.git
              clone-on-startup: true

```

## Lab 3 - Encrypting sensitive information
1. Add the `encrypt.key` property in `bootstrap.properties` file
