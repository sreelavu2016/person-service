package com.person.dto;

import javax.xml.bind.annotation.XmlRootElement;


public class Person {
	
private long id;
	
	private String name;
	
	private int age;
	
	private double salary;	
	

	public Person() {
		super();
	}



	public Person(long id, String name, int age, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public double getSalary() {
		return salary;
	}



	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
	

}
