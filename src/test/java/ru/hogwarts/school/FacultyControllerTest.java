//package ru.hogwarts.school;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import ru.hogwarts.school.model.Faculty;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest(classes = HogwartsSchoolSpringApplicationTests.class,
//webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class FacultyControllerTest {
//
//    @LocalServerPort
//	private int port;
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Test
//    void create() {
//        ResponseEntity<Faculty> response = testRestTemplate.postForEntity("/faculty",
//                new Faculty(null, "math", "green"),
//                Faculty.class);
//
//        assertThat(response).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody().getColor()).isEqualTo("green");
//    }
//}
