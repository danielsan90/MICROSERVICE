package com.fraud;

import com.clients.fraud.FraudResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FraudService {
    @Autowired
    FraudRepository fraudRepository;

    public FraudResponse isFraudulentCustomer(Integer customerId){
        FraudResponse f= new FraudResponse();
        f.setIsFraudulent(false);
        fraudRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return f;
    }
}
