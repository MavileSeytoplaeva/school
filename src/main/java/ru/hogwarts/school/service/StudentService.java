package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
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

    private Integer count = 0;


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

    public List<String> findStudentNameStartWithA() {
        Collection<Student> students = studentRepository.findAll();
        return students.stream()
                .filter(student -> student.getName().startsWith("A"))
                .map(student -> student.getName())
                .sorted()
                .toList();
    }

    public int getAverageAgeByStream() {
        Collection<Student> students = studentRepository.findAll();
        return (int) students.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(Double.NaN);
    }

    public void getAllStudentsForThreads() {
        List<Student> students = studentRepository.findAll();
        logger.info(students.get(0).toString());
        logger.info(students.get(1).toString());

        new Thread(() -> {
            logger.info(students.get(2).toString());
            logger.info(students.get(3).toString());
        }).start();

        new Thread(() -> {
            logger.info(students.get(4).toString());
            logger.info(students.get(5).toString());
        }).start();
    }

    public void syncThreads() {
        synchronized (StudentService.class) {
            printStudent(0);
            printStudent(1);

            new Thread(() -> {
                printStudent(2);
                printStudent(3);
            }).start();

            new Thread(() -> {
                printStudent(4);
                printStudent(5);
            }).start();

        }
    }

    private void printStudent(int num) {
        synchronized (StudentService.class) {
            List<Student> students = studentRepository.findAll();
            logger.info(students.get(num).toString() + " Count " + count);
            count++;
        }
    }

}

