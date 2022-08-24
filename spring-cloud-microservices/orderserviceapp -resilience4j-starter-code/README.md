# Setting up Resilience4j dependency for OrderService application
1. Setting up the dependency
```xml
  <dependency>
      <groupId>io.github.resilience4j</groupId>
      <artifactId>resilience4j-spring-boot2</artifactId>
      <version>1.3.0</version>
  </dependency>
```
2. Configure the resilience4j configuration in `application.yaml` file
```yaml
resilience4j:
  circuitbreaker:
    instances:
      inventoryservice:
        register-health-indicator: true
        ring-buffer-size-in-closed-state: 5
        ring-buffer-size-in-half-open-state: 3
        wait-duration-in-open-state: 30s
        failure-rate-threshold: 50
        record-exceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
          - org.springframework.web.client.ResourceAccessException
          - java.lang.IllegalStateException

```
3. Enable the actuator endpoints for circuit breaker
```yaml
management:
  endpoint:
    health:
      show-details: always
  health:
    circuitbreakers:
      enabled: true
  endpoints:
    web:
      exposure:
        include: "*"
```

4. Add the `CircuitBreaker` annotation in the `saveOrder` method in `OrderServiceImpl` class
```java
    @Override
    @CircuitBreaker(name = "inventoryservice")
    public Order saveOrder(Order order) {
            //discoveryClient();
            loadBalancedUsingRestTemplate();
            //inventoryFeignClient.updateQty();
            return this.orderDAO.save(order);
    }
```

5. Hit the `POST` request to save the Order. Since the Inventory micorservice is down, the Circuit breaker should open the circuit

```
http://localhost:8222/api/v1/orders
```
```
{
        "totalPrice": 40000,
        "orderDate": "2020-06-16",
        "lineItems": [
            {
                "qty": 1,
                "price": 55000
            }
        ]
    }
```
6. Open the Actuator at `http://localhost:8222/actuator/health` endpoint


## Retry
7.  Add this configuration inside the `application.yaml` file
```
resilience4j:
  retry:
      instances:
        retryService:
          max-retry-attempts: 4
          wait-duration: 5000
          retry-exceptions:
            - java.io.IOException
            - java.util.concurrent.TimeoutException
            - org.springframework.web.client.ResourceAccessException
            - java.lang.IllegalStateException
```
8. In `OrderServiceImpl` class add the below annotation on `saveOrder` method

```
    @Override
    //@CircuitBreaker(name = "inventoryservice", fallbackMethod = "fallBack")
    @Retry(name = "retryService", fallbackMethod = "fallBack")
    public Order saveOrder(Order order) {
        System.out.println(" Calling the instance of inventory service");
            //discoveryClient();
            loadBalancedUsingRestTemplate();
            //inventoryFeignClient.updateQty();
            return this.orderDAO.save(order);
    }
```
9. Provide a fallBack method implementation which should provide the same cotract as of the method

```
 public Order fallBack(Throwable throwable){
        return Order.builder().orderId(1111).orderDate(LocalDate.now()).totalPrice(30000).build();
    }

```
