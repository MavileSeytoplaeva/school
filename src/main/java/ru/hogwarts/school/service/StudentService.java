package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);


    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Collection<Student> getFiveLastStudents() {

        logger.debug("Getting five last students");
        return studentRepository.getFiveLastStudents();
    }

    public Long getAmountOfStudents() {

        logger.debug("Getting amount of students");
        return studentRepository.getAmountOfStudents();
    }

    public Long getAverageAge() {

        logger.debug("Getting average age");
        return studentRepository.getAverageAge();
    }

    public Student createStudent(Student student) {

        logger.debug("Creating student");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {

        logger.debug("Finding student by id {}", id);
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {

        logger.debug("Editing student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {

        logger.debug("Deleting student by id {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> studentsWithAge(int age) {

        logger.debug("Getting students with age {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findAgeBetween(Integer min, Integer max) {
        logger.debug("Getting students with age between {} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<Student> getAllPageable(Integer pageNumber, Integer pageSize) {
        logger.debug("Getting all students by pageNumber {} and pageSize {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        return studentRepository.findAll(pageRequest).getContent();
    }
    public Collection<Student> getAll() {
        logger.debug("Getting all students");

        return studentRepository.findAll();
    }



    public Faculty findStudentsFaculty(Long id) {
        logger.debug("Getting students faculty by id {}", id);
        Student student = studentRepository.findById(id).get();
        return facultyRepository.findById(student.getFaculty().getId()).get();
    }

    public Set<Student> findStudentsByFacultyId(Long id) {

        logger.debug("Getting students by faculty id {}", id);
        return studentRepository.findStudentByFaculty_id(id);
    }

    public List<Student> findStudentByName(String name) {
        logger.debug("Getting students with name {}", name);
        return studentRepository.findStudentByNameIgnoreCase(name);
    }

}

