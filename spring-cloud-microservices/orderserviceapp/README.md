# Setting up Decryption at sprig cloud config client application
1. `TODO-1` Setting up the dependency
```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-rsa</artifactId>
</dependency>
```
2. ``TODO-2` In the `bootstrap.yaml` add the `encrypt.key`
```yaml
encrypt:
  key: TESTING
```

6. Start the `Config server` and `OrderService` application. You should be able to access the application pointing to `QA` profile

7. Verfify the `OrderService` application is running on port `8222` 

8. Hit the `http://localhost:8222/api/v1/orders/` in the browser and you should get a proper `200` response. 


## Lab to communicate with Inventory service

1. Create a configuration class called `ApplicationConfiguration`

2. Return a `RestTemplate` Bean

3. In the `OrderServiceImpl`
  - Autowire the `DiscoveryClient` instance
  - Autowire the `RestTemplate` instance
  - Inside the `saveOrder` method
  ```java
    @Override
  public Order saveOrder(Order order) {
    List<ServiceInstance> inventoryservices = this.discoveryClient.getInstances("inventoryservice");

  if (inventoryservices != null && inventoryservices.size() > 0 ) {
      //invoke the inventory service using the rest template
      String uri = inventoryservices.get(0).getUri().toString();
      log.info("URI :: "+ uri);
  }
  ```

## Service to Service communication

###  There are three ways to communicate with the services
  
  1. `DiscoveryClient` 

  ```java
      @Override
    public Order saveOrder(Order order) {

        List<ServiceInstance> inventoryservices = this.discoveryClient.getInstances("inventoryservice");

        if (inventoryservices != null && inventoryservices.size() > 0 ) {
            //invoke the inventory service using the rest template
            String uri = inventoryservices.get(0).getUri().toString() +"/api/v1/inventory";
            log.info("URI :: "+ uri);
            Object response = this.restTemplate.postForEntity(uri, null, Object.class);
            log.info("Response :: "+ ((ResponseEntity<Integer>)response).getBody());

        }*/
       return this.orderDAO.save(order);
    }
```

2. To Use the `@LoadBalancer` on the `RestTemplate`

- In the config package, create a `AppConfiguration` 
```java
@Configuration
public class ApplicationConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```
- Inside the `OrderServiceImpl`, add the below code 
```java
 @Override
    public Order saveOrder(Order order) {
        Object response = this.restTemplate.postForEntity("http://inventoryservice/api/v1/inventory", null, Object.class);
        log.info("Response :: "+ ((ResponseEntity<Integer>)response).getBody());
```

3. Use the FeignClient 
- Add the maven dependency 
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```
- Enable FeignClient in the Root class
```java
@SpringBootApplication
@EnableFeignClients
public class SpringBootDemoApplication implements ApplicationRunner {...}
```

- Create an Interface and annotate with `@FeignClient("servicename")`
```java
@FeignClient("inventoryservice")
public interface InventoryFeignClient {

    @PostMapping("/api/v1/inventory")
    ResponseEntity<Integer> updateQty();
}
```
- In the `OrderServiceImpl` inject the `FeignClient` and call like a regular java method invocation
```java
@Service
public class OrderServiceImpl implements OrderService {

    private InventoryFeignClient inventoryFeignClient;

    @Override
    public Order saveOrder(Order order) {

        ResponseEntity<Integer> response = this.inventoryFeignClient.updateQty();
        log.info("Response through Feign client:: {}", response.getBody().toString());
```


## Resiliency and Fault tolerant

### Setting up Resliency4j with OrderService

1. Dependencies for resilience4j
```
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot2</artifactId>
    <version>1.3.0</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

2. Add the @CircuitBreaker annotation on top of calling service 
```
@Override
@CircuitBreaker(name="inventoryservice")
public Order saveOrder(Order order) {
```

3. Add the configuration properties inside the `application.yaml` file
```
resilience4j:
  circuitbreaker:
    instances:
      inventoryservice:
        register-health-indicator: true
        ring-buffer-size-in-closed-state: 5
        ring-buffer-size-in-half-open-state: 5
        wait-duration-in-open-state: 30s
        failure-rate-threshold: 50
        record-exception:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
          - org.springframework.web.client.ResourceAccessException
```

4. Expose the health and circuitebreaker actuator end points in bootstrap.yaml 
```
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
  health:
    circuitbreakers:
      enabled: true
```
5. Verify the health endpoint for the circuit breaker status at 
```http://localhost:8333/actuator/health```

6. Optinally, provide a fallback method implementation, which takes `Throwable` as additional argument 
```java 
  private Order fallBackImpl(Order order, Throwable exception){
        return Order.builder().orderId(1111).orderDate(LocalDate.now()).totalPrice(23000).build();
    }
```

7. Configure the fallback method in the `@CircuitBreaker` annotation
```java

    @Override
    @CircuitBreaker(name="inventoryservice", fallbackMethod = "fallBackImpl")
    public Order saveOrder(Order order) {
```

8. Bring down the `inventory service` and check the transition of circuit from `CLOSE` to `HALF-OPEN` to `OPEN` state 

9. Bring up the `inventory service` and check the transition of circuit from `OPEN` to `HALF-OPEN` to `CLOSE` state 

10. Observe that when the circuit is in `OPEN` state, the `inventoryservice` is not called by `Resilicenc4j`.

## Setting up retries with Resilience4J Retry

1. Dependencies for resilience4j retry
```
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-retry</artifactId>
    <version>1.3.0</version>
</dependency>
```

2. Add the @Retry annotation on top of calling service 
```
@Override
@Retry(name="inventoryserviceRetry")
public Order saveOrder(Order order) {
```

3. Add the configuration properties inside the `application.yaml` file
```
resilience4j:
  retry:
    instances:
      inventoryserviceretry:
        max-retry-attempts: 4
        wait-duration: 2000
        retry-exceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
          - org.springframework.web.client.ResourceAccessException
```

4. Expose the health and circuitebreaker actuator end points in bootstrap.yaml 
```
management:
  endpoints:
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
  health:
    circuitbreakers:
      enabled: true
```

5. Optinally, provide a fallback method implementation, which takes `Throwable` as additional argument 
```java 
  private Order fallBackImpl(Order order, Throwable exception){
        return Order.builder().orderId(1111).orderDate(LocalDate.now()).totalPrice(23000).build();
    }
```

6. Configure the fallback method in the `@Retry` annotation
```java

    @Override
    @Retry(name="inventoryservice", fallbackMethod = "fallBackImpl")
    public Order saveOrder(Order order) {
```

8. Bring down the `inventory service` and check the logs in OrderService

9. Observe that the retry attempts are made by orderservice for 4 times with the configured interval time.




