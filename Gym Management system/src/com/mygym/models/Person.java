package com.mygym.models;

public abstract class Person 
{
	protected String id;
	protected String firstName, lastName;
	protected String birthDate;
	
	public Person(String id, String fn, String ln, String bdate) {
		this.id = id;
		firstName = fn;
		lastName = ln;
		birthDate = bdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}