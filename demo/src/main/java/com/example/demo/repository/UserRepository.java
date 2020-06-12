package com.example.demo.repository;

import com.example.demo.model.Project;
import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public List<User> findByVisibleProjects(Project project);

}
