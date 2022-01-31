package com.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
external clients
 */


@FeignClient("fraud")
public interface FraudClient {

    @GetMapping(path = "api/v1/fraud-check/{customerId}")
    FraudResponse isFraudster(@PathVariable("customerId") Integer customerId);

}

/*

Declarative REST Client: Feign = simulare

Feign is a declarative web service client. It makes writing web service clients easier. To use Feign create an interface and annotate it. I


In the @FeignClient annotation the String value ("fraud" above) is an arbitrary client name, which is used to create
a Spring Cloud LoadBalancer client.
You can also specify a URL using the url attribute (absolute value or just a hostname).
The name of the bean in the application context is the fully qualified name of the interface.
To specify your own alias value you can use the qualifiers value of the @FeignClient annotation.

The load-balancer client above will want to discover the physical addresses for the "fraud" service.
If your application is a Eureka client then it will resolve the service in the Eureka service registry.
If you don’t want to use Eureka, you can configure a list of servers in your external configuration using SimpleDiscoveryClient.

 */
