package com.finzly.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
		String s = String.valueOf(System.currentTimeMillis());
		customer.setCustomerId("CUS" + s.substring(5, s.length()));
		return customerRepository.save(customer);
	}

	public Customer getCustomerDetails(String customerId) {
		return customerRepository.findById(customerId).get();
	}
	public Customer getCustomerDetails(String email, String password) {
		 List<Customer> customerList = customerRepository.findByEmailAndPassword(email, password);
		 if(customerList.isEmpty()) {
			 return new Customer();
		 }
		return customerRepository.findByEmailAndPassword(email, password).get(0);
	}

	public List<Loan> getLoansByCustomerId(String customerId) {
		List<Loan> loans = new ArrayList<Loan>();
		loanRepository.findByCustomerId(customerId).forEach(loan -> loans.add(loan));
		return loans;
	}

	public List<PaymentSchedule> getPaymentScheduleByLoanId(String loanId) {
		List<PaymentSchedule> paymentScheduleList = new ArrayList<PaymentSchedule>();
		paymentScheduleRepository.findAll().forEach(paymentSchedule -> {
			if (paymentSchedule.getLoanId().equals(loanId)) {
				paymentScheduleList.add(paymentSchedule);
			}
		});
		return paymentScheduleList;
	}

	public Loan saveLoan(Loan loan)  {
		loan.setInterestRate(10);
		String s = String.valueOf(System.currentTimeMillis());
		loan.setLoanId("FINZ" + s.substring(5, s.length()));
		try {
			createPaymentSchedule((Loan)loan.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return loanRepository.save(loan);

	}

	private void createPaymentSchedule(Loan loan) {
		String paymentTerm = loan.getPaymentTerm();
		if (paymentTerm.equals("Interest Only")) {
			createInterestOnlySchedule(loan);
		} else if (paymentTerm.equals("Even Principal")) {

		}
	}

	private void createInterestOnlySchedule(Loan loan) {
		List<PaymentSchedule> paymentScheduleList = new ArrayList<PaymentSchedule>();
		int interest = loan.getProjectedInterest() / loan.getPaymentSchedule();
		for (int i = 0; i < loan.getPaymentSchedule(); i++) {
			PaymentSchedule paymentSchedule = new PaymentSchedule();
			paymentSchedule.setLoanId(loan.getLoanId());
			paymentSchedule.setPaymentDate(calculatePaymentDate(loan, loan.getPaymentFrequency()));
			paymentSchedule.setPrincipal(0);
			paymentSchedule.setPaymentStatus("PROJECTED");
			paymentSchedule.setPaymentAmount(interest);
			paymentSchedule.setProjectedInterest(calculateProjectedInterest(loan));
			paymentScheduleList.add(paymentSchedule);
		}
		paymentScheduleRepository.saveAll(paymentScheduleList);
	}

	private float calculateProjectedInterest(Loan loan) {
		int paymentSchedule = loan.getPaymentSchedule();
		int principal=loan.getLoanAmount();
		int years = loan.getLoanDuration();
		float interestRate = loan.getInterestRate();
		int interestAmount =(int) ((principal * years  * interestRate) / 100)/(paymentSchedule*12);
		principal = principal - (principal / loan.getPaymentSchedule());
		loan.setLoanAmount(principal);
		return interestAmount;
	}

	private String calculatePaymentDate(Loan loan, String paymentFrequency) {
		String paymentDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(loan.getStartDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		switch (paymentFrequency) {
		case "Monthly": {
			now.add(Calendar.MONTH, 1);
			paymentDate = "" + now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
					+ now.get(Calendar.YEAR);
			break;
		}
		case "Quarterly": {
			now.add(Calendar.MONTH, 3);
			paymentDate = "" + now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
					+ now.get(Calendar.YEAR);
			break;
		}
		case "Half Yearly": {
			now.add(Calendar.MONTH, 6);
			paymentDate = "" + now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
					+ now.get(Calendar.YEAR);
			break;
		}
		case "Yearly": {
			now.add(Calendar.MONTH, 12);
			paymentDate = "" + now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
					+ now.get(Calendar.YEAR);
			break;
		}

		}
		loan.setStartDate(""+now.get(Calendar.YEAR)+ "-" + (now.get(Calendar.MONTH) + 1) + "-"+ now.get(Calendar.DATE));
		return paymentDate;
	}



}
