package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final HashMap<Long, Student> students = new HashMap<>(Map.of());
    private long lastId = 0;

    public Student createStudent(Student student) {
        student.setId(lastId++);
        students.put(student.getId(), student);
        return student;
    }

    public Student findStudent(long lastId) {
        if (students.containsKey(lastId)) {
            return students.get(lastId);
        }
        return null;
    }

    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {
        if (students.containsKey(id)){
            students.remove(id);
            return students.get(id);
        }
        return null;
    }

    public List<Long> studentsWithAge(int age) {
        return students.entrySet().stream()
                .filter(e -> e.getValue().getAge() == age)
                .map(Map.Entry::getKey)
                .toList();
    }
}

