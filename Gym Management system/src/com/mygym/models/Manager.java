package com.mygym.models;

public class Manager extends Worker
{
	private static final int salary = 80; //NIS per hour
	
	public Manager(String id, String fn, String ln, String bdate) {
		super(id, fn, ln, bdate, "MANAGER");
	}
	
	public static int getSalary() {
		return salary;
	}
}