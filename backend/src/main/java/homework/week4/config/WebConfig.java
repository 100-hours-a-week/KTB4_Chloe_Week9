package homework.week4.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.profile-upload-dir}")
    private String profileUploadDir;

    @Value("${file.post-upload-dir}")
    private String postUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/profile/**")
                .addResourceLocations("file:" + profileUploadDir + "/");

        registry.addResourceHandler("/images/post/**")
                .addResourceLocations("file:" + postUploadDir + "/");
    }
}
