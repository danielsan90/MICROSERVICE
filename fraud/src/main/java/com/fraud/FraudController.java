package com.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
public class FraudController {

    @Autowired
    FraudService fraudService;

    @GetMapping(path = "{customerId}")
    public FraudResponse isFraudster(
            @PathVariable("customerId") Integer customerId) {
        return fraudService.isFraudulentCustomer(customerId);
    }
}