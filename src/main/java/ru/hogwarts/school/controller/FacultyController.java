package ru.hogwarts.school.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Faculty>> getAll(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String color) {
        if ((name != null && !name.isBlank()) || (color != null && !color.isBlank())) {
            return ResponseEntity.ok(facultyService.findByNameOrColor(name, color));
        }
        return ResponseEntity.ok(facultyService.getAll());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findStudentsByFaculty(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        try {
            facultyService.findFaculty(id);
        } catch (NoSuchElementException | EmptyResultDataAccessException e) {
            throw new FacultyNotFoundException();
        }
        return ResponseEntity.ok(facultyService.findFaculty(id));
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.editFaculty(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Collection<Faculty>> findFacultyWithSameColor(@PathVariable String color) {
        if (facultyService.facultyWithColor(color) == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.facultyWithColor(color));
    }

    @GetMapping("/name/longest")
    public ResponseEntity<String> getLongestFacultyName() {
        return ResponseEntity.ok(facultyService.getLongestFacultyName());
    }
}

