package homework.week4.FileUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileStorage implements FileStorageService {

    @Value("${file.profile-upload-dir}")
    private String profileUploadDir;

    @Value("${file.post-upload-dir}")
    private String postUploadDir;

    @Override
    public String storeProfileImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {

            //파일의 원본명 가져옴
            String originalFilename = file.getOriginalFilename();

            String extension = extractExtension(originalFilename); // ".jpg" 등만 추출
            String storedFilename = UUID.randomUUID() + extension; // 예측 불가능한 이름으로 저장


            Path directoryPath = Paths.get(profileUploadDir).toAbsolutePath().normalize();
            Path savePath = directoryPath.resolve(storedFilename).normalize();

            // 최종 경로가 이미지 저장 폴더 안에 있는지 검증
            if (!savePath.startsWith(directoryPath)) {
                // 저장 경로가 이상한건 서버에서 확인해주면 됨 -> 사용자 한테는 500으로 에러 응답 감
                throw new IllegalStateException("저장 경로가 업로드 디렉토리를 벗어났습니다.");
            }

            try {
                Files.createDirectories(directoryPath); //폴더가 없으면 새로 생성
                file.transferTo(savePath.toFile());
            } catch (IOException e) {
                throw new RuntimeException("파일 저장에 실패했습니다.", e);
            }

            return storedFilename; // 전체 경로가 아니라 파일명(또는 상대경로)만 반환
        }
        return null;
    }

    public String storePostImage(MultipartFile file){
        if (file != null && !file.isEmpty()) {

            //파일의 원본명 가져옴
            String originalFilename = file.getOriginalFilename();

            String extension = extractExtension(originalFilename); // ".jpg" 등만 추출
            String storedFilename = UUID.randomUUID() + extension; // 예측 불가능한 이름으로 저장


            Path directoryPath = Paths.get(postUploadDir).toAbsolutePath().normalize();
            Path savePath = directoryPath.resolve(storedFilename).normalize();

            // 최종 경로가 이미지 저장 폴더 안에 있는지 검증
            if (!savePath.startsWith(directoryPath)) {
                // 저장 경로가 이상한건 서버에서 확인해주면 됨 -> 사용자 한테는 500으로 에러 응답 감
                throw new IllegalStateException("저장 경로가 업로드 디렉토리를 벗어났습니다.");
            }

            try {
                Files.createDirectories(directoryPath);
                file.transferTo(savePath.toFile());
            } catch (IOException e) {
                throw new RuntimeException("파일 저장에 실패했습니다.", e);
            }

            return storedFilename; // 전체 경로가 아니라 파일명(또는 상대경로)만 반환
        }
        return null;
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
