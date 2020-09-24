package com.finzly.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import com.finzly.model.Customer;
@EnableJpaRepositories
public interface CustomerRepository extends CrudRepository<Customer, String> {

}
