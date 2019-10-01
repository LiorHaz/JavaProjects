package com.mygym.models;

public class Trainer extends Worker
{
	private final static int salary = 40; //NIS per hour
	
	public Trainer(String id, String fn, String ln, String bdate) {
		super(id, fn, ln, bdate, "TRAINER");
	}
	
	public static int getSalary() {
		return salary;
	}
}