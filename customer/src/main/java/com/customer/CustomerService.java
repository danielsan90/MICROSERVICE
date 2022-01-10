package com.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestTemplate restTemplate;


    public void registerCustomer(Customer c){

        //check if is fraud
        FraudResponse fraudResponse=restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudResponse.class,
                c.getId()
        );
        if(fraudResponse.getIsFraudulent()){
            throw new IllegalStateException("fraudster");
        }
        customerRepository.save(c);
    }

}
