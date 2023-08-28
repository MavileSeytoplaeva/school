package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Set;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;



    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Collection<Student> getFiveLastStudents() {
        return studentRepository.getFiveLastStudents();
    }

    public Long getAmountOfStudents() {
        return studentRepository.getAmountOfStudents();
    }

    public Long getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> studentsWithAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findAgeBetween(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Faculty findStudentsFaculty(Long id) {
        Student student = studentRepository.findById(id).get();
        return facultyRepository.findById(student.getFaculty().getId()).get();
    }

    public Set<Student> findStudentsByFacultyId(Long id) {
        return studentRepository.findStudentByFaculty_id(id);
    }
}

