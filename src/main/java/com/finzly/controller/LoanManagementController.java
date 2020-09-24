package com.finzly.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finzly.model.Customer;
import com.finzly.model.Loan;
import com.finzly.model.PaymentSchedule;
import com.finzly.service.LoanManagementService;

/**
 * @author boojith
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class LoanManagementController {
	@Autowired
	LoanManagementService loanManagementService;

	/**
	 * Creating a get mapping that retrieves the details of particular customer
	 * 
	 * @param customerId
	 * @return
	 */
	@GetMapping("/customer/{customerId}")
	private Customer getCustomerDetails(@PathVariable("customerId") String customerId) {
		return loanManagementService.getCustomerDetails(customerId);
	}

	/**
	 * Creating a post mapping that save the details of particular customer
	 * 
	 * @param customer
	 * @return Customer
	 */
	@PostMapping("/add-customer")
	private Customer saveCustomer(@RequestBody Customer customer) {
		System.out.println("Save Customer");
		return loanManagementService.saveCustomerDetails(customer);
	}

	/**
	 * Creating a get mapping that retrieves the loans of particular customer
	 * 
	 * @param customerId
	 * @return List<Loan>
	 */
	@GetMapping("/loans/{customerId}")
	private List<Loan> getLoans(@PathVariable("customerId") String customerId) {
		return loanManagementService.getLoansByCustomerId(customerId);
	}

	/**
	 * Creating a get mapping that retrieves the PaymentSchedule of a particular
	 * Loan
	 * 
	 * @param loanId
	 * @return List<PaymentSchedule>
	 */
	@GetMapping("/loan/paymentSchedule/{loanId}")
	private List<PaymentSchedule> getPaymentSchedule(@PathVariable("loanId") String loanId) {
		return loanManagementService.getPaymentScheduleByLoanId(loanId);
	}

	/**
	 * creating post mapping that post the loan detail of a customer in the database
	 * 
	 * @param loan
	 * @return 
	 * @return
	 */
	@PostMapping("/loan")
	private Loan saveLoan(@RequestBody Loan loan) {
		System.out.println("Save Loan");
		return loanManagementService.saveLoan(loan);

	}

}
