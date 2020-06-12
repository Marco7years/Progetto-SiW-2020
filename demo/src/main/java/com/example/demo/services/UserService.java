package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User getUser(Long id) {
		Optional<User> result = this.userRepository.findById(id);
		return result.orElse(null);
	}
	
//	This method saves a user in a DB
//	@param user the user to save in the DB
//	@return the saved user
//	@throws DataIntegrityViolationException if a User with the same username as the passed User already exists in the DB		
	@Transactional
	public User saveUser(User user)  {
		return this.userRepository.save(user);
	}
	
	@Transactional
	public List<User> findAllusers()  {
		Iterable<User> i = this.userRepository.findAll();
		ArrayList<User> lista = new ArrayList<>();
		for(User u : i) {
			lista.add(u);
		}
		return lista;
	}

	public List<User> getMembers(Long projectId) {
		// TODO Auto-generated method stub
		return null;
	}

}
