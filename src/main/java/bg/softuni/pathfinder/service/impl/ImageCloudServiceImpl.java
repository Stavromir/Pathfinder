package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.service.ImageCloudService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageCloudServiceImpl implements ImageCloudService {


    private Cloudinary cloudinary;

    public ImageCloudServiceImpl() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dd0ghbz3j",
                "api_key", "196252561524548",
                "api_secret", "OMv2kz32YRUUdMSsDsl7qZ-0N1U",
                "secure", true));
    }

    @Override
    public String saveImageInCloud(MultipartFile multipartFile) throws IOException {

        String imageId = UUID.randomUUID().toString();

        Map params = ObjectUtils.asMap(
                "public_id", imageId,
                "overwrite", true,
                "resource_type", "image"
        );

        File tmpFile = new File(imageId);

        Files.write(tmpFile.toPath(), multipartFile.getBytes());
        cloudinary.uploader().upload(tmpFile, params);
        Files.delete(tmpFile.toPath());

        String url = cloudinary.url().imageTag(imageId).split("'")[1];

        return url;
    }
}
