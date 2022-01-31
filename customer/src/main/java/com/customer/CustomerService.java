package com.customer;

import com.clients.fraud.FraudClient;
import com.clients.fraud.FraudResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    private FraudClient fraudClient;



    public void registerCustomer(Customer c){

        //check if is fraud

        //with rest template
        /* FraudResponse fraudResponse=restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudResponse.class,
                c.getId()
        );*/

        //with feignClient
        FraudResponse fraudResponse=fraudClient.isFraudster(c.getId());

        if(fraudResponse.getIsFraudulent()){
            throw new IllegalStateException("fraudster");
        }
        customerRepository.save(c);
    }

}
