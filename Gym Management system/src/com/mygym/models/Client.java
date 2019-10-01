package com.mygym.models;

public class Client extends Person
{
	private int subscriptionCode = -1;
	private String subscriptionType;
	private String creditCard;
	private int monthlyPayment;
	
	public Client(String firstName, String lastName, String birthDate, int subscriptionCode, String subscriptionType,
			String id, String creditCard, int monthlyPayment) {
		
		this(firstName, lastName, birthDate, subscriptionType, id, creditCard, monthlyPayment);
		this.subscriptionCode = subscriptionCode;
	}
	
	public Client(String firstName, String lastName, String birthDate, String subscriptionType,
			String id, String creditCard, int monthlyPayment) {
		/**
		 * Create client without clientCode for now, used when the client has no code yet because
		 * The code is auto-create by the database and not by the user or application.
		 */
		super(id, firstName, lastName, birthDate);
		this.subscriptionType = subscriptionType;
		this.creditCard = creditCard;
		this.monthlyPayment = monthlyPayment;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getSubscriptionCode() {
		return subscriptionCode;
	}

	public void setSubscriptionCode(int subscriptionCode) {
		/**
		 * set only when the code is not initialized yet..
		 * the code is unique and should not be changed..
		 */
		if(subscriptionCode == -1)
			this.subscriptionCode = subscriptionCode;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	
	public int getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(int monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
}