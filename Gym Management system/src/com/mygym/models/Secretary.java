package com.mygym.models;

public class Secretary extends Worker
{
	private static final int salary = 35; //NIS per hour
	
	public Secretary(String id, String fn, String ln, String bdate) {
		super(id, fn, ln, bdate, "SECRETARY");
	}
	
	public static int getSalary() {
		return salary;
	}
}