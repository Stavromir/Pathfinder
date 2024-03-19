package bg.softuni.pathfinder.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageCloudService {

    String saveImageInCloud(MultipartFile multipartFile) throws IOException;
}
