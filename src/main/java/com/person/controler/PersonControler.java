package com.person.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.person.dto.Person;
import com.person.exception.CustomErrorType;
import com.person.exception.PersonNotFoundException;

import com.person.service.PersonService;

@RestController
@RequestMapping("/api")
public class PersonControler {
	
	public static final Logger logger = LoggerFactory.getLogger(PersonControler.class);
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("/person")
	public ResponseEntity<List<Person>> listAllPerson(){
		
		List<Person> persons = personService.findAllUsers();
		
		if(persons.isEmpty()) {
			logger.error("User not found.");
			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
			
		}
		return new ResponseEntity<List<Person>>(persons,HttpStatus.OK);
		
		
	}
	
	// -------------------Retrieve Single User------------------------------------------

		@RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
		public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
			logger.info("Fetching User with id {}", id);
			Person person = personService.findById(id);
			if (person == null) {
				logger.error("User with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("User with id " + id 
						+ " not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Person>(person, HttpStatus.OK);
		}
	
		@RequestMapping(value = "/person/", method = RequestMethod.POST)
		public ResponseEntity<?> createUser(@RequestBody Person person, UriComponentsBuilder ucBuilder) {
			logger.info("Creating Person : {}", person);

			if (personService.isUserExist(person)) {
				logger.error("Unable to create. A Person with name {} already exist", person.getName());
				return new ResponseEntity(new CustomErrorType("Unable to create. A Person with name " + 
				person.getName() + " already exist."),HttpStatus.CONFLICT);
			}
			personService.saveUser(person);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/api/person/{id}").buildAndExpand(person.getId()).toUri());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}

		// ------------------- Update a User ------------------------------------------------

		@RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
		public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Person person) {
			logger.info("Updating User with id {}", id);

			Person currentPerson = personService.findById(id);

			if (currentPerson == null) {
				logger.error("Unable to update. User with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}

			currentPerson.setName(person.getName());
			currentPerson.setAge(person.getAge());
			currentPerson.setSalary(person.getSalary());

			personService.updateUser(currentPerson);
			return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
		}

		// ------------------- Delete a User-----------------------------------------

		@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
			logger.info("Fetching & Deleting User with id {}", id);

			Person person = personService.findById(id);
			if (person == null) {
				logger.error("Unable to delete. User with id {} not found.", id);
				return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
						HttpStatus.NOT_FOUND);
			}
			personService.deleteUserById(id);
			return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
		}

		// ------------------- Delete All Users-----------------------------

		@RequestMapping(value = "/person/", method = RequestMethod.DELETE)
		public ResponseEntity<Person> deleteAllUsers() {
			logger.info("Deleting All Users");

			personService.deleteAllUsers();
			return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
		}


	

}
