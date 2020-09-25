package com.finzly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import com.finzly.model.Customer;

@EnableJpaRepositories
public interface CustomerRepository extends CrudRepository<Customer, String> {
	List<Customer> findByEmailAndPassword(String email, String password);

}
