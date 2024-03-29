package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findByStudentId(long studentId);

    void deleteStudentById(long id);
}
