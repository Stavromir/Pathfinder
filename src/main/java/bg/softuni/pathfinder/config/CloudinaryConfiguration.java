package bg.softuni.pathfinder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfiguration {

    private String apiKey;
    private String apiSecret;
    private String cloudName;

    public String getApiKey() {
        return apiKey;
    }

    public CloudinaryConfiguration setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public CloudinaryConfiguration setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
        return this;
    }

    public String getCloudName() {
        return cloudName;
    }

    public CloudinaryConfiguration setCloudName(String cloudName) {
        this.cloudName = cloudName;
        return this;
    }
}
