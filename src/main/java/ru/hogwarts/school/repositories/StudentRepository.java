package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT COUNT(*) FROM STUDENT", nativeQuery = true)
    Long getAmountOfStudents();

    @Query(value = "SELECT AVG(age) FROM STUDENT", nativeQuery = true)
    Long getAverageAge();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getFiveLastStudents();

    Collection<Student> findByAgeBetween(Integer min, Integer max);

    Collection<Student> findByAge(Integer age);

    Set<Student> findStudentByFaculty_id(Long id);

}
