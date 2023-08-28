package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
class HogwartsSchoolSpringApplicationTests {

//	@LocalServerPort
//	private int port;
//
//	@Autowired
//	FacultyController facultyController;
//
//	@Autowired
//	private TestRestTemplate testRestTemplate;
//
//	@Test
//	void create() {
//		ResponseEntity<Faculty> response = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty",
//				new Faculty(null, "math", "green"),
//				Faculty.class);
//
//		assertThat(response).isEqualTo(HttpStatus.OK);
//		assertThat(response.getBody()).isNotNull();
//		assertThat(response.getBody().getColor()).isEqualTo("green");
//	}

	@Test
	void contextLoads() {
	}

}


//package ru.hogwarts.school;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import ru.hogwarts.school.controller.StudentController;
//
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//
//@SpringBootTest
//class HogwartsSchoolSpringApplicationTests {
////
////	@LocalServerPort
////	private int port;
////
////	@Autowired
////	private StudentController studentController;
////
////	@Autowired
////	private TestRestTemplate testRestTemplate;
//
//	@Test
//	void contextLoads(){
//	}
////
////	@Test
////	void getAllEndpoint() throws Exception {
////		Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
////				.isNotNull();
////	}
//////	@Test
//////	void getFacultyEndpoint() throws Exception {
//////		int id = 3;
//////		Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "faculty/{id}", String.class))
//////				.isNotNull();
//////	}
////	@Test
////	void getIdEndpoint() throws Exception {
////		int id = 3;
////		Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/{%d}",id, String.class))
////				.isNotNull();
////	}
////
////
//}
