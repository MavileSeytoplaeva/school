package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
	private Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getAll() {
        ResponseEntity<List> response = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty", List.class);
        assertNotNull(response);
    }

    @Test
    void getFacultyById() {
        ResponseEntity<Faculty> response = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/1", Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("greefindor");
        assertThat(response.getBody().getColor()).isEqualTo("red");
    }

    @Test
    void getFacultyByColor() {
        ResponseEntity<Collection> response = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/color/red", Collection.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response).isNotNull();
    }

    @Test
    void getFacultyStudents() {
        ResponseEntity<Set> response = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/students/1", Set.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void create() {
        Faculty faculty = new Faculty();
        faculty.setName("Math");
        faculty.setColor("Blue");

        ResponseEntity<Faculty> response = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getColor()).isEqualTo(faculty.getColor());
        assertThat(response.getBody().getName()).isEqualTo(faculty.getName());

    }
    @Test
    void deleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("russian");
        faculty.setColor("rose");

        ResponseEntity<Faculty> postedFacultyResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(postedFacultyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postedFacultyResponse.getBody().getName()).isEqualTo("russian");

        ResponseEntity<Void> deleteResponse = testRestTemplate.exchange("http://localhost:" + port + "/faculty/" + postedFacultyResponse.getBody().getId() , HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        ResponseEntity<Faculty> tryToFindFacultyResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + postedFacultyResponse.getBody().getId(), Faculty.class);

        assertThat(tryToFindFacultyResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
