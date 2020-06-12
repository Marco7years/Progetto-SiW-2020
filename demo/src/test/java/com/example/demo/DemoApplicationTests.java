package com.example.demo;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.ProjectService;
import com.example.demo.services.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
class DemoApplicationTests {

	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	
	@Before
	public void deleteAll() {
		userRepository.deleteAll();
		taskRepository.deleteAll();
		projectRepository.deleteAll();
		System.out.println("All dleleted");
	}
	
	@Test
	public void testUpdateUser() {
		
		//save first user in the database
		User user1 = new User("mario", "rossi");
		user1 = userService.saveUser(user1);
		Assert.assertEquals(user1.getId().longValue(), 1l);
		Assert.assertEquals(user1.getFirstname(), "mario");
		Assert.assertEquals(user1.getLastname(), "rossi");
		
		//update first user in the database
		User user1updated = new User("maria", "rossi");
		user1updated.setId(user1.getId());
		user1updated = userService.saveUser(user1updated);
		user1updated = userService.getUser(user1updated.getId());
		Assert.assertEquals(user1updated.getId().longValue(), 1l);
		Assert.assertEquals(user1updated.getFirstname(), "maria");
		Assert.assertEquals(user1updated.getLastname(), "rossi");
		
		//save second user in the database
		User user2 = new User("luca", "bianchi");
		user2 = userService.saveUser(user2);
		Assert.assertEquals(user2.getId().longValue(), 2L);
		Assert.assertEquals(user2.getFirstname(), "luca");
		Assert.assertEquals(user2.getLastname(), "bianchi");
		
		//save first project in the database
		Project project1 = new Project("TestProject1", "just a little test project");
		project1.setOwner(user1);
		project1 = projectService.saveProject(project1);
		Assert.assertEquals(project1.getOwner(), user1);
		Assert.assertEquals(project1.getDescription(), "just a little test project");
		Assert.assertEquals(project1.getName(), "TestProject1");
		
		//save second project in the database
		Project project2 = new Project("TestProject2", "just a little test project");
		project2.setOwner(user1);
		project2 = projectService.saveProject(project2);
		Assert.assertEquals(project2.getOwner(), user1);
		Assert.assertEquals(project2.getDescription(), "just a little test project");
		Assert.assertEquals(project2.getName(), "TestProject2");
		
		//give visibility to user2 over project1
		project1 = projectService.shareProjectWithUser(project1, user2);
		
		//test projects owned by user1
		List<Project> projects = projectRepository.findByOwner(user1updated);
		Assert.assertEquals(projects.size(), 2);
		Assert.assertEquals(projects.get(0), project1);
		Assert.assertEquals(projects.get(1), project2);
		
		//test projects visible by user2
		List<User> project1members = userRepository.findByVisibleProjects(project1);
		Assert.assertEquals(project1members.size(), 1);
		Assert.assertEquals(project1members.get(0), user2);
		
		List<Project> projectsVisibleByUser2 = projectRepository.findByMembers(user2);
		Assert.assertEquals(projectsVisibleByUser2.size(), 1);
		Assert.assertEquals(projectsVisibleByUser2.get(0), project1);
	}

}
