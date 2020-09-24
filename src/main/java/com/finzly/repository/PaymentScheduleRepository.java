package com.finzly.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import com.finzly.model.PaymentSchedule;
@EnableJpaRepositories 
public interface PaymentScheduleRepository extends CrudRepository<PaymentSchedule, Integer> {

}
