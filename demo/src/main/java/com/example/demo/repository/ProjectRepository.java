package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Project;
import com.example.demo.model.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	public List<Project> findByMembers(User member);
	
	public List<Project> findByOwner(User owner);

}
