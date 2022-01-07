package com.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public void registerCustomer(@RequestBody Customer customer){
       log.info("new customer {} ", customer);
       customerService.registerCustomer(customer);

    }

}
