package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    public StudentService studentService;

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Creating faculty");

        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.debug("Finding faculty by id {}", id);

        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Editing faculty");

        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.debug("Deleting faculty by id {}", id);

        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> facultyWithColor(String color) {
        logger.debug("Getting faculty by color {}", color);

        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getAll() {
        logger.debug("Getting all faculties");

        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.debug("Finding faculty by name {} or color {}", name, color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> findStudentsByFaculty(long id) {
        logger.debug("Getting faculty students by faculty id {}", id);

        return studentService.findStudentsByFacultyId(id);
    }

}

