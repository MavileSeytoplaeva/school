package ru.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @Test
    public void testGetAll() throws Exception {
        Student student1 = new Student("draco", 11);
        Student student2 = new Student("harry", 12);

        List<Student> students = new ArrayList<>(List.of(student1, student2));

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .content(students.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("draco"))
                .andExpect(jsonPath("$[0].age").value(11))
                .andExpect(jsonPath("$[1].name").value("harry"))
                .andExpect(jsonPath("$[1].age").value(12));
    }

    @Test
    public void getStudentsFacultyTest() throws Exception{
        Long idF = 1L;
        String nameF = "russian";
        String colorF = "rose";

        Long idS = 1L;
        String nameS = "draco";
        int ageS = 11;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", nameF);
        facultyObject.put("color", colorF);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", nameS);
        studentObject.put("age", ageS);


        Faculty faculty = new Faculty();
        faculty.setColor(colorF);
        faculty.setName(nameF);

        Student student = new Student();
        student.setId(idS);
        student.setAge(ageS);
        student.setName(nameS);
        student.setFaculty(faculty);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/1")
                        .accept(studentObject.toString(), facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(nameF))
                .andExpect(jsonPath("$.color").value(colorF));
    }

    @Test
    public void getStudentTest() throws Exception{
        Long id = 1L;
        String name = "Draco";
        int age = 11;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student(name, age);
        student.setId(id);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void createStudentTest() throws Exception{
        Long id = 1L;
        String name = "Draco";
        int age = 11;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student") //send
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteStudentTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void findStudentWithSameAgeTest() throws Exception{
        Student student1 = new Student("draco", 11);
        Student student2 = new Student("harry", 11);

        Set<Student> students = new HashSet<>(Set.of(student1, student2));

        when(studentRepository.findByAge(any(Integer.class))).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/11")
                        .content(students.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("draco"))
                .andExpect(jsonPath("$[0].age").value(11))
                .andExpect(jsonPath("$[1].name").value("harry"))
                .andExpect(jsonPath("$[1].age").value(11));
    }

    @Test
    public void getFiveLastStudentsTest() throws Exception{
        Student student1 = new Student(1L,"draco", 11);
        Student student2 = new Student(2L, "harry", 11);
        Student student3 = new Student(3L, "student3", 12);
        Student student4 = new Student(4L, "student4", 13);
        Student student5 = new Student(5L, "student5", 14);

        Set<Student> students = new HashSet<>(Set.of(student1, student2, student3, student4, student5));

        when(studentRepository.getFiveLastStudents()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get-five-last-students")
                        .content(students.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4L))
                .andExpect(jsonPath("$[1].id").value(3L))
                .andExpect(jsonPath("$[2].id").value(1L))
                .andExpect(jsonPath("$[3].id").value(2L))
                .andExpect(jsonPath("$[4].id").value(5L));
    }

    @Test
    public void getAverageAgeTest() throws Exception {
        when(studentRepository.getAverageAge()).thenReturn(23L);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get-average-age"))
                .andExpect(status().isOk());

        Assertions.assertEquals(23,studentService.getAverageAge());
    }

    @Test
    public void getAmountOfStudentsTest() throws Exception{
        when(studentRepository.getAmountOfStudents()).thenReturn(10L);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/amount-of-students"))
                .andExpect(status().isOk());

        Assertions.assertEquals(10L, studentService.getAmountOfStudents());
    }


}