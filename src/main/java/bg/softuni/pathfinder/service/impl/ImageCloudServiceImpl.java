package bg.softuni.pathfinder.service.impl;

import bg.softuni.pathfinder.config.CloudinaryConfiguration;
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


    private final Cloudinary cloudinary;
    private final CloudinaryConfiguration cloudinaryConfiguration;

    public ImageCloudServiceImpl(CloudinaryConfiguration cloudinaryConfiguration) {
        this.cloudinaryConfiguration = cloudinaryConfiguration;

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinaryConfiguration.getCloudName(),
                "api_key", cloudinaryConfiguration.getApiKey(),
                "api_secret", cloudinaryConfiguration.getApiSecret(),
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
