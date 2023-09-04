package ru.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentService studentService;

    @SpyBean
    private FacultyService facultyService;

    @Test
    public void testGetAll() throws Exception {
        Faculty faculty1 = new Faculty("Faculty1", "Red");
        Faculty faculty2 = new Faculty("Faculty2", "Blue");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyRepository.findAll()).thenReturn(faculties);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .content(faculties.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Faculty1"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[1].name").value("Faculty2"))
                .andExpect(jsonPath("$[1].color").value("Blue"));
    }

    @Test
    public void getFacultyStudentsTest() throws Exception {
        Student student1 = new Student("draco", 11);
        Student student2 = new Student("harry", 12);

        Set<Student> students = new HashSet<>(Set.of(student1, student2));

        when(studentService.findStudentsByFacultyId(any(Long.class))).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty//students/1")
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
    public void getFacultyTest() throws Exception{
        Long id = 1L;
        String name = "russian";
        String color = "rose";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void saveFacultyTest() throws Exception {
        Long id = 1L;
        String name = "russian";
        String color = "rose";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setColor(color);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void deleteFacultyTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void getFacultiesByColor() throws Exception{
        Faculty faculty1 = new Faculty("math", "blue");
        Faculty faculty2 = new Faculty("biology", "blue");

        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyRepository.findByColorIgnoreCase(any(String.class))).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color/blue")
                        .accept(faculties.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("math"))
                .andExpect(jsonPath("$[0].color").value("blue"))
                .andExpect(jsonPath("$[1].name").value("biology"))
                .andExpect(jsonPath("$[1].color").value("blue"));

    }
}