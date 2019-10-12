package com.person.service;

import java.util.List;

import com.person.dto.Person;


public interface PersonService {
	
	Person findById(long id);

	Person findByName(String name);

	void saveUser(Person person);

	void updateUser(Person user);

	void deleteUserById(long id);

	List<Person> findAllUsers();

	void deleteAllUsers();

	boolean isUserExist(Person person);

}
