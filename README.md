# microservices-spring-cloud  
Spring cloud project, using bounded context defined apps  
This project practices several concepts and tech provided by spring cloud archetecture. It uses:  
* Service registry with Eureka, with RestTemplate and service discory for communication between services  
* Spring config server, requesting config files by http from this repository  
* Client side load balancer using Spring Feign, with FeignClient interfaces  
* Distributed tracing with Spring Sleught and Papertrail  
