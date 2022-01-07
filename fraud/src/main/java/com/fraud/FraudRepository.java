package com.fraud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudRepository extends JpaRepository<FraudCheckHistory, Integer> {
}
