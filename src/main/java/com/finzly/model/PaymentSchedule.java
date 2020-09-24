package com.finzly.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class PaymentSchedule {
	@Id
	@Column
	private String paymentId;
	@Column
	private String loanId;
	@Column
	private String paymentDate;
	@Column
	private int principal;
	@Column
	private float projectedInterest;
	@Column
	private String paymentStatus;
	@Column
	private int paymentAmount;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getPrincipal() {
		return principal;
	}

	public void setPrincipal(int principal) {
		this.principal = principal;
	}

	public float getProjectedInterest() {
		return projectedInterest;
	}

	public void setProjectedInterest(float projectedInterest) {
		this.projectedInterest = projectedInterest;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

}
