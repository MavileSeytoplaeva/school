package ru.hogwarts.school;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void getAll() {
        ResponseEntity<Collection> response = testRestTemplate.getForEntity("http://localhost:" + port + "/student", Collection.class);
        assertNotNull(response);
    }

    @Test
    void getStudentsFaculty() {
        ResponseEntity<Faculty> response = testRestTemplate.getForEntity("http://localhost:" + port + "/student/faculty/6", Faculty.class);

        assertNotNull(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getColor()).isEqualTo("green");
        assertThat(response.getBody().getName()).isEqualTo("slyz");
    }

    @Test
    void getStudent() {
        ResponseEntity<Student> response = testRestTemplate.getForEntity("http://localhost:" + port + "/student/2", Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("hermo");
        assertThat(response.getBody().getAge()).isEqualTo(4);
    }

    @Test
    void createStudent() {
        Student student = new Student();
        student.setAge(20);
        student.setName("mavile");

        ResponseEntity<Student> response = testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAge()).isEqualTo(20);
        assertThat(response.getBody().getName()).isEqualTo("mavile");
    }

    @Test
    void deleteStudent() {
        Student student = new Student();
        student.setName("student");
        student.setAge(20);

        ResponseEntity<Student> postedStudentResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        assertThat(postedStudentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Void> deleteResponse = testRestTemplate.exchange("http://localhost:" + port + "/student/" + postedStudentResponse.getBody().getId() , HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());

        ResponseEntity<Student> tryToFindStudentResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + postedStudentResponse.getBody().getId(), Student.class);

        assertThat(tryToFindStudentResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findStudentsWithSameAge() {
        ResponseEntity<Collection> response = testRestTemplate.getForEntity("http://localhost:" + port + "/student/age/4", Collection.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response);
    }
}
