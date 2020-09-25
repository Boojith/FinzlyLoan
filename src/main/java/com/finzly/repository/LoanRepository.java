package com.finzly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import com.finzly.model.Customer;
import com.finzly.model.Loan;

@EnableJpaRepositories
public interface LoanRepository extends CrudRepository<Loan, String> {

	List<Loan> findByCustomerId(String customerId);
}
