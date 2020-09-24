package com.finzly.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finzly.model.Customer;
import com.finzly.model.Loan;
import com.finzly.model.PaymentSchedule;
import com.finzly.repository.CustomerRepository;
import com.finzly.repository.LoanRepository;
import com.finzly.repository.PaymentScheduleRepository;

@Service
public class LoanManagementService {
	@Autowired
	LoanRepository loanRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PaymentScheduleRepository paymentScheduleRepository;

	public Customer saveCustomerDetails(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer getCustomerDetails(String customerId) {
		return customerRepository.findById(customerId).get();
	}

	public List<Loan> getLoansByCustomerId(String customerId) {
		List<Loan> loans = new ArrayList<Loan>();
		loanRepository.findAll().forEach(loan -> loans.add(loan));
		return loans;
	}

	public List<PaymentSchedule> getPaymentScheduleByLoanId(String loanId) {
		List<PaymentSchedule> paymentScheduleList = new ArrayList<PaymentSchedule>();
		paymentScheduleRepository.findAll().forEach(paymentSchedule -> {
			if(paymentSchedule.getLoanId().equals(loanId)) {
				paymentScheduleList.add(paymentSchedule);
			}
		});
		return paymentScheduleList;
	}

	public Loan saveLoan(Loan loan) {
		System.out.println("Loan Service");
		loan.setLoanId("LUS13566");
		return loanRepository.save(loan);

	}

}
