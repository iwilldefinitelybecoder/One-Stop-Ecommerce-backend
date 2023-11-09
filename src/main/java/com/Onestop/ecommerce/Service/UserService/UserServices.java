package com.Onestop.ecommerce.Service.UserService;

import com.Onestop.ecommerce.Controller.userController.ImgDownloadResponse;
import com.Onestop.ecommerce.Controller.userController.ImgUploadRequest;
import com.Onestop.ecommerce.Entity.user.UserProfileIcon;
import com.Onestop.ecommerce.Entity.user.userEntity;
import com.Onestop.ecommerce.Repository.userRepo.UserProfileIconRepo;
import com.Onestop.ecommerce.Repository.userRepo.UserRepository;
import com.Onestop.ecommerce.utils.CompressandDecompressFile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServices  {

    private final UserRepository userRepository;

    private final UserProfileIconRepo userProfileIconRepo;
    public  String uploadProfileIcon(ImgUploadRequest request) {
        userEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        UserProfileIcon icon = userProfileIconRepo.findByUserEntityId(user.getId()).orElse(null);

        log.info("User is {}",user);
        if(user == null){
            return "NULL";
        }
        String[] format = {"image/png","image/jpeg","image/jpg"};
        if(!Arrays.asList(format).contains(request.getImage().getContentType())){
            return "INVALID_FORMAT";
        }
        String imageName = generateImageName(Objects.requireNonNull(request.getImage().getOriginalFilename()));
        byte[] imageBytes = ConvertMultiPartFileToBytes(request.getImage());
        if(icon != null) {
            icon.setImageData(imageBytes);
            icon.setImageName(imageName);
            userProfileIconRepo.save(icon);
            return "SUCCESS";
        }
            var userProfileIcon = UserProfileIcon.builder()
                    .imageName(imageName)
                    .imageData(imageBytes)
                    .userEntity(user)
                    .imageType(request.getImage().getContentType())
                    .build();
            userProfileIconRepo.save(userProfileIcon);

       user.setImageId(userProfileIcon.getId());
        userRepository.save(user);
        return "SUCCESS";


    }

    private byte[] ConvertMultiPartFileToBytes(MultipartFile image) {
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = CompressandDecompressFile.compressImage(image.getBytes());
        } catch (Exception e) {
            log.error("Error occurred while converting multipart file to bytes");
        }
        return imageBytes;
    }

    private String generateImageName(String originalFilename) {

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(currentDate);

        String FileExtenstion = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = date +"-blog-"+ FileExtenstion;
        return fileName;
    }

    public ImgDownloadResponse getImage(Long id) {
       Optional <UserProfileIcon> userProfileIcon = userProfileIconRepo.findById(id);
        if (userProfileIcon.isPresent()) {
            byte[] imageData = CompressandDecompressFile.decompressImage(userProfileIcon.get().getImageData());
            ImgDownloadResponse response = ImgDownloadResponse.builder()
                    .fileName(userProfileIcon.get().getImageName())
                    .imageData(imageData)
                    .fileType(userProfileIcon.get().getImageType())
                    .size(imageData.length)
                    .build();
            log.info("file type is {}",response.getFileName());
            return response;

        } else {
            return null;
        }
    }


}