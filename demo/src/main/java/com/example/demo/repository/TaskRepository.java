package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

}
