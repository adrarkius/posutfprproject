package io.gkuhn.userbroker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.gkuhn.userbroker.dao.UserRepository;
import io.gkuhn.userbroker.model.User;

@Controller
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(path="/{userId}", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody User getByUser(@PathVariable(value="userId", required = true)int id) {
		User response = userRepository.findByUserid(id);
		return response;
	}
	
	@RequestMapping(value="/", consumes="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<User> sendUser(@RequestBody User user) {
		User userReturn = userRepository.save(user);
		return (new ResponseEntity<User>(userReturn, HttpStatus.CREATED));
	}

	
}