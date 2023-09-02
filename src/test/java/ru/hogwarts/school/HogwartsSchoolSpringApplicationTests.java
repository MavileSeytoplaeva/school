package ru.hogwarts.school;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest
class HogwartsSchoolSpringApplicationTests {

	//	@LocalServerPort
//	private int port;
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FacultyRepository facultyRepository;

	@SpyBean
	private FacultyService facultyService;

	@InjectMocks
	private FacultyController facultyController;


	@Test
	void contextLoads() {
	}

	private Collection<Faculty> faculties = new ArrayList<>(List.of(
			new Faculty("russian", "rose"),
			new Faculty("biology", "purple")
	));

	@Test
	void getAll() {
		when(facultyService.getAll()).thenReturn(faculties);

		assertThat(facultyController.getAll("russian", "rose")).isEqualTo(faculties);
	}

	@Test
	public void saveFacultyTest() throws Exception {
		Long id = 1L;
		String name = "russian";

		JSONObject facultyObject = new JSONObject();
		facultyObject.put("name", name);

		Faculty faculty = new Faculty();
		faculty.setColor("rose");
		faculty.setName("russian");

		when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
		when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

		mockMvc.perform(MockMvcRequestBuilders
						.post("/faculty") //send
						.content(facultyObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()) //receive
//                .andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.name").value(name));
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
