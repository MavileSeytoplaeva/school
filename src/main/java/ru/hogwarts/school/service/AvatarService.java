package ru.hogwarts.school.service;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
//@Profile("production")
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private StudentRepository studentRepository;
    private AvatarRepository avatarRepository;

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Collection<String> getAll(Integer pageNumber, Integer pageSize) {
        logger.debug("Requesting avatars with pageNumber {} and pageSize {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Collection<Avatar> avatars = avatarRepository.findAll(pageRequest).getContent();
        Collection<String> originalFileNames = new ArrayList<>();
        for (Avatar avatar : avatars) {
            String originalFileName = avatar.getOriginalFileName();
            originalFileNames.add(originalFileName);
        }
        return originalFileNames;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.debug("Uploading avatar for student with id {} ", studentId);
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setOriginalFileName(avatarFile.getOriginalFilename());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setPreview(generateImagePreview(filePath));
        avatarRepository.save(avatar);
    }

    public byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    public void deleteAvatar(long id) {
        avatarRepository.deleteById(id);
    }




    public Avatar findAvatar(Long studentId) {
        logger.debug("Finding avatar for student with id {}", studentId);
        if (avatarRepository.findByStudentId(studentId) == null){
            return new Avatar();
        } else {
            return avatarRepository.findByStudentId(studentId);
        }
    }
    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
