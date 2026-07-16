package homework.week4.FileUpload;

import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {

    String storeProfileImage(MultipartFile image);

    String storePostImage(MultipartFile file);
}
