# Setting up DiscoveryClient for OrderService application
1. Setting up the dependency
```xml
 <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
2. Create a `bootstrap.yaml` file inside `src/main/resources` directory
3. Configure the `spring.application.name`, `spring.profiles.active` and `spring.cloud.config.uri` to point to config server
```yaml
spring:
  application:
    name: orderservice
  profiles:
    active: qa
  cloud:
    config:
      uri: http://localhost:8888
```
4. Move all the configurations from `src/main/resources` to `orderservice` under `gitlab` which is managed by `config server`
5. In the `application.properties` add the below configuration specific to Eureka server
```properties
eureka.instance.preferIpAddress = true
eureka.client.registerWithEureka = true
eureka.client.fetchRegistry = true
eureka.client.serviceUrl.defaultZone = http://localhost:8761/eureka/
server.port=8222
```

6. Start the `Config server`, `Eureka Server` and `OrderService` application. You should be able to access the application pointing to `QA` profile

7. Verify if the `ORDERSERVICE` is showing up in the `Eureka` dashboard

8. Hit the `http://localhost:8761/eureka/apps` in the browser and you should see the details in `json` format

Lab:
===

1. Branch name 

```
 Orderservice - service-service-communication
 InventoryService - eureka-client-registration 
```

2. 
 - Create two instances of Inventory service  [9222, 9333]        
 -   Create one instance of Orderservice - 8222

3. Navigate to OrderServiceImpl
   - Scanrio - 1  In the save order method, comment the 
   - In the ApplicationConfiguration comment the @LoadBalanced on restTemplate method 
   ```
            discoveryClient();
            //loadBalancedUsingRestTemplate();
            //inventoryFeignClient.updateQty();
    ```
    - Scanrio - 2  In the save order method, comment the 
    - In the ApplicationConfiguration uncomment the @LoadBalanced on restTemplate method 
      ```
            //discoveryClient();
            loadBalancedUsingRestTemplate();
            //inventoryFeignClient.updateQty();
      ```   
    - Scnario - 3 In the save order method,
       - In the ApplicationConfiguration uncomment the @LoadBalanced on restTemplate method 
       - Use the feignclient
       ```
            //discoveryClient();
            //loadBalancedUsingRestTemplate();
            inventoryFeignClient.updateQty();
        ```    

```
Payload:
POST method: http://localhost:8222/api/v1/orders
```
```
{
    "orderId": 1,
    "totalPrice": 40000,
    "orderDate": "2020-06-16",
    "lineItems": [
        {
            "id": 2,
            "qty": 1,
            "price": 55000
        }
    ]
}
```            
