package com.finzly.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import com.finzly.model.Loan;
@EnableJpaRepositories
public interface LoanRepository extends CrudRepository<Loan, String> {

}
