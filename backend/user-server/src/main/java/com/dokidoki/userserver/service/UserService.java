package com.dokidoki.userserver.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dokidoki.userserver.dto.request.ProfileImageRequest;
import com.dokidoki.userserver.dto.response.SuperRichRes;
import com.dokidoki.userserver.entity.UserEntity;
import com.dokidoki.userserver.enumtype.ProviderType;
import com.dokidoki.userserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    @Transactional(readOnly = true)
    public Optional<UserEntity> getUserFromSubAndProvider(String sub, ProviderType type){
        return userRepository.findBySubAndProviderType(sub,type);
    }
    @Transactional
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    /*
        사용자 프로필 관련 서비스
     */
    public String readProfileImage(Long userId) {
        // 사용자 찾기
        UserEntity userEntity = getUserById(userId).orElse(null);
        if (userEntity == null)
            return null;

        // 사용자가 갖는 프로필 이미지 반환
        return userEntity.getPicture();
    }

    @Transactional
    public String createProfileImage(Long memberId, MultipartFile multipartFile) {
        // 사용자 객체 가져오기
        UserEntity userEntity = getUserById(memberId).orElse(null);
        if (userEntity == null)  // 존재하지 않는다면 null 반환
            return null;

        // 파일 업로드 및 URL 획득
        String profileImageUrl = uploadImage(multipartFile, "profiles");
        if (profileImageUrl == null)
            return null;

        // 기존 객체의 URL 수정하기 위해 새 UserEntity 생성
        UserEntity newUserEntity = UserEntity.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .providerType(userEntity.getProviderType())
                .sub(userEntity.getSub())
                .picture(profileImageUrl)
                .point(userEntity.getPoint())
                .build();

        // 업데이트
        saveUser(newUserEntity);

        return profileImageUrl;
    }

    /*
        Aws S3 업로드 관련 서비스
     */
    public String uploadImage(MultipartFile multipartFile, String dirName) {
        // 사진 파일이 아닐 경우 종료
        if (!multipartFile.getContentType().startsWith("image/")) {
            return null;
        }

        // S3 업로드 할 파일 이름 생성
        String s3FileName = dirName + "/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            // MultipartFile -> File 변환
            File convertFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 변환 실패"));

            // S3 업로드 및 URL 획득
            String url = putS3(convertFile, s3FileName);

            // 로컬 파일 삭제
            removeTempFile(convertFile);

            return url;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<SuperRichRes> getSuperRichList(Integer page, Integer size){
        Page<UserEntity> userEntities = userRepository.findAll(
                PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "point"))
        );

        List<SuperRichRes> superRichRes = new ArrayList<>();

        userEntities.forEach(
                (user)->{
                    superRichRes.add(
                            SuperRichRes.builder()
                                    .point(user.getPoint())
                                    .encryptName(encryptedUserName(user.getName())) // 유저 이름을 암호화 해서 넣는다
                                    .build()
                    );
                }
        );
        return superRichRes;
    }

    // 유저 이름 암호화
    private String encryptedUserName(String name){
        // 이름이 세글자 이상일 때 암호화한다.
        if(name ==null || name.length() <3) return name;

        int nameLen = name.length();
        // 가운데 2/3 지점을 *로 암호화한다.
        // 가운데 지점이 제일 길게
        int  middleLength = nameLen/3 + nameLen%3;

        StringBuilder sb = new StringBuilder();
        sb.append(name.substring(0,nameLen/3));
        for(int i = 0; i<middleLength; i++) sb.append('*');
        sb.append(name.substring(nameLen/3 + middleLength));

        return sb.toString();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(
                        bucket, fileName, uploadFile
                ).withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeTempFile(File targetFile) {
        if (!targetFile.delete()) {
            log.warn("파일이 삭제되지 않았습니다.");
        }
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        // 로컬 파일 생성 및 반환
        File convertFile = new File(UUID.randomUUID().toString());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
