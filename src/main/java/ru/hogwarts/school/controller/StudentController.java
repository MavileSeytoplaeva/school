package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }



    @GetMapping("get-five-last-students")
    public ResponseEntity<Collection<Student>> getFiveLastStudents() {
        return ResponseEntity.ok(studentService.getFiveLastStudents());
    }

    @GetMapping("get-average-age")
    public Long getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("amount-of-students")
    public Long getAmountOfStudents() {
        return studentService.getAmountOfStudents();
    }


    @GetMapping()
    public ResponseEntity<Collection<Student>> getStudents(@RequestParam(required = false) Integer min,
                                                           @RequestParam(required = false) Integer max,
                                                           @RequestParam(required = false) Integer pageNumber,
                                                           @RequestParam(required = false) Integer pageSize) {
        if (min != null && max != null) {
            return ResponseEntity.ok(studentService.findAgeBetween(min, max));
        }
        if (pageNumber != null && pageSize != null) {
            return ResponseEntity.ok(studentService.getAllPageable(pageNumber,pageSize));
        }
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("faculty/{id}")
    public ResponseEntity<Faculty> getStudentsFaculty(@PathVariable long id) {
        return ResponseEntity.ok(studentService.findStudentsFaculty(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        try {
            studentService.findStudent(id);
        } catch (NoSuchElementException e){
            throw new StudentNotFoundException();
        }
        return ResponseEntity.ok(studentService.findStudent(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.editStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<Collection<Student>> findStudentWithSameAge(@PathVariable Integer age) {
        if (studentService.studentsWithAge(age) == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.studentsWithAge(age));
    }


}

