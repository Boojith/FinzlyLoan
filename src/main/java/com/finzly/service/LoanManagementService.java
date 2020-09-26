package com.finzly.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(LoanManagementService.class);

	/**
	 * saveCustomerDetails is used to add a new customer to DB
	 * 
	 * @param customer
	 * @return Customer
	 */
	public Customer saveCustomerDetails(Customer customer) {
		customer.setCustomerId(generateKey("CUS"));
		return customerRepository.save(customer);
	}

	/**
	 * getCustomerDetails is used to get details of a particular customer
	 * 
	 * @param customerId
	 * @return Customer
	 */
	public Customer getCustomerDetails(String customerId) {
		logger.info("Getting customer details for {}", customerId);
		return customerRepository.findById(customerId).get();
	}

	/**
	 * getCustomerDetails is used to verify existing customer
	 * 
	 * @param email
	 * @param password
	 * @return Customer
	 */
	public Customer getCustomerDetails(String email, String password) {
		List<Customer> customerList = customerRepository.findByEmailAndPassword(email, password);
		if (customerList.isEmpty()) {
			return new Customer();
		}
		logger.info("Verifying existing customer details for {}", email);
		return customerRepository.findByEmailAndPassword(email, password).get(0);
	}

	/**
	 * getLoansByCustomerId is used to loans of a particular customer
	 * 
	 * @param customerId
	 * @return List<Loan>
	 */
	public List<Loan> getLoansByCustomerId(String customerId) {
		List<Loan> loans = new ArrayList<Loan>();
		loanRepository.findByCustomerId(customerId).forEach(loan -> loans.add(loan));
		logger.info("Getting loan details for existing customer {}", customerId);
		return loans;
	}

	/**
	 * getPaymentScheduleByLoanId is used to fetch PaymentSchedule of a loan
	 * 
	 * @param loanId
	 * @return List<PaymentSchedule>
	 */
	public List<PaymentSchedule> getPaymentScheduleByLoanId(String loanId) {
		logger.info("Getting Payment Schedule for Loan{}", loanId);
		return paymentScheduleRepository.findByLoanId(loanId);
	}

	/**
	 * updatePaymentStatus method is used to update payment status for a particular
	 * loan
	 * 
	 * @param paymentId
	 * @return PaymentSchedule
	 */
	public PaymentSchedule updatePaymentStatus(String paymentId) {
		Optional<PaymentSchedule> paymentScheduleOptional = paymentScheduleRepository.findById(paymentId);
		PaymentSchedule paymentSchedule = new PaymentSchedule();
		if (paymentScheduleOptional.isPresent()) {
			paymentSchedule = paymentScheduleOptional.get();
			paymentSchedule.setPaymentStatus("PAID");
		}
		logger.info("Updating payment status for payment schedule {}", paymentId);
		return paymentScheduleRepository.save(paymentSchedule);
	}

	/**
	 * saveLoan is used to add a loan to DB
	 * 
	 * @param loan
	 * @return Loan
	 */
	public Loan saveLoan(Loan loan) {
		loan.setInterestRate(10);
		loan.setLoanId(generateKey("FINZ"));
		try {
			createPaymentSchedule((Loan) loan.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		logger.info("New Loan {} created for the customer", loan);
		return loanRepository.save(loan);

	}

	/**
	 * createPaymentSchedule is used to create payment schedule based on PaymentTerm
	 * 
	 * @param loan
	 */
	private void createPaymentSchedule(Loan loan) {
		String paymentTerm = loan.getPaymentTerm();
		if (paymentTerm.equals("Interest Only")) {
			createInterestOnlySchedule(loan);
		} else if (paymentTerm.equals("Even Principal")) {
			createEvenPrincipalSchedule(loan);
		}
	}

	/**
	 * This method is used to create Even Principal Schedule
	 * 
	 * @param loan
	 */
	private void createEvenPrincipalSchedule(Loan loan) {
		List<PaymentSchedule> paymentScheduleList = new ArrayList<PaymentSchedule>();
		int perPaymentPrincipal = loan.getLoanAmount() / loan.getPaymentSchedule();
		for (int i = 1; i <= loan.getPaymentSchedule(); i++) {
			PaymentSchedule paymentSchedule = new PaymentSchedule();
			paymentSchedule.setPaymentId(generateKey("PAY"));
			paymentSchedule.setLoanId(loan.getLoanId());
			paymentSchedule.setPaymentDate(calculatePaymentDate(loan, loan.getPaymentFrequency()));
			paymentSchedule.setPrincipal(loan.getLoanAmount());
			paymentSchedule.setProjectedInterest(calculateInterest(loan,perPaymentPrincipal));
			paymentSchedule.setPaymentStatus("PROJECTED");
			paymentSchedule.setPaymentAmount(paymentSchedule.getProjectedInterest()+perPaymentPrincipal);
			paymentScheduleList.add(paymentSchedule);
		}
		logger.info("Creating Even Principal Schedule for Loan {}", loan);
		paymentScheduleRepository.saveAll(paymentScheduleList);
	}

	/**
	 * This method is used to create Interest Only Schedule
	 * 
	 * @param loan
	 */
	private void createInterestOnlySchedule(Loan loan) {
		List<PaymentSchedule> paymentScheduleList = new ArrayList<PaymentSchedule>();
		int netPrincipal = loan.getLoanAmount();
		int perPaymentPrincipal = loan.getLoanAmount() / loan.getPaymentSchedule();
		for (int i = 1; i <= loan.getPaymentSchedule(); i++) {
			PaymentSchedule paymentSchedule = new PaymentSchedule();
			paymentSchedule.setPaymentId(generateKey("PAY"));
			paymentSchedule.setLoanId(loan.getLoanId());
			paymentSchedule.setPaymentDate(calculatePaymentDate(loan, loan.getPaymentFrequency()));
			paymentSchedule.setProjectedInterest(calculateInterest(loan,perPaymentPrincipal));
			if(i==loan.getPaymentSchedule()) {
				paymentSchedule.setPrincipal(netPrincipal);
				paymentSchedule.setPaymentAmount((paymentSchedule.getProjectedInterest())+(netPrincipal));
			}else {
				paymentSchedule.setPrincipal(0);
				paymentSchedule.setPaymentAmount(paymentSchedule.getProjectedInterest());
			}
			paymentSchedule.setPaymentStatus("PROJECTED");
			paymentScheduleList.add(paymentSchedule);
		}
		logger.info("Creating Interest Only  Schedule for Loan {}", loan);
		paymentScheduleRepository.saveAll(paymentScheduleList);
	}

	/**
	 * This method is used to calculate the projected Interest based on schedule
	 * 
	 * @param loan
	 * @param perPaymentPrincipal 
	 * @return interestAmount
	 */
	private float calculateInterest(Loan loan, int perPaymentPrincipal) {
		float paymentSchedule = loan.getPaymentSchedule();
		float principal = loan.getLoanAmount();
		float years = loan.getLoanDuration();
		float interestRate = loan.getInterestRate();
		int interestAmount =(int) ((principal * (years / paymentSchedule) * interestRate) / 100);
		principal = principal - perPaymentPrincipal;
		loan.setLoanAmount((int)principal);
		return interestAmount;
	}

	/**
	 * calculatePaymentDate is used to calculate the Payment date based on the loan
	 * duration
	 * 
	 * @param loan
	 * @param paymentFrequency
	 * @return paymentDate
	 */
	private String calculatePaymentDate(Loan loan, String paymentFrequency) {
		String paymentDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = formatter.parse(loan.getStartDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar paymentDateCalenar = Calendar.getInstance();
		paymentDateCalenar.setTime(date);
		switch (paymentFrequency) {
		case "Monthly": {
			paymentDateCalenar.add(Calendar.MONTH, 1);
			paymentDate = "" + paymentDateCalenar.get(Calendar.DATE) + "-"
					+ (paymentDateCalenar.get(Calendar.MONTH) + 1) + "-" + paymentDateCalenar.get(Calendar.YEAR);
			break;
		}
		case "Quarterly": {
			paymentDateCalenar.add(Calendar.MONTH, 3);
			paymentDate = "" + paymentDateCalenar.get(Calendar.DATE) + "-"
					+ (paymentDateCalenar.get(Calendar.MONTH) + 1) + "-" + paymentDateCalenar.get(Calendar.YEAR);
			break;
		}
		case "Half Yearly": {
			paymentDateCalenar.add(Calendar.MONTH, 6);
			paymentDate = "" + paymentDateCalenar.get(Calendar.DATE) + "-"
					+ (paymentDateCalenar.get(Calendar.MONTH) + 1) + "-" + paymentDateCalenar.get(Calendar.YEAR);
			break;
		}
		case "Yearly": {
			paymentDateCalenar.add(Calendar.MONTH, 12);
			paymentDate = "" + paymentDateCalenar.get(Calendar.DATE) + "-"
					+ (paymentDateCalenar.get(Calendar.MONTH) + 1) + "-" + paymentDateCalenar.get(Calendar.YEAR);
			break;
		}

		}
		paymentDate = convertDateFormat(paymentDate);
		loan.setStartDate(paymentDate);
		return paymentDate;
	}

	/**
	 * Used to convert paymentDate to corresponding date format
	 * 
	 * @param paymentDate
	 * @return paymentDate
	 */
	private String convertDateFormat(String paymentDate) {
		if (paymentDate.charAt(1) == '-') {
			paymentDate = "0" + paymentDate;
		}
		if (paymentDate.charAt(4) == '-') {
			paymentDate = paymentDate.substring(0, 3) + "0" + paymentDate.substring(3);
		}
		return paymentDate;
	}
	
	private String generateKey(String prefix) {
		String key = UUID.randomUUID().toString().split("-")[0];
		return prefix+ key;
	}

}
