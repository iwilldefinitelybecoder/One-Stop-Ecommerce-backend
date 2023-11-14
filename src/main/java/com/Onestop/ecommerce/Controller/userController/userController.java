package com.Onestop.ecommerce.Controller.userController;

import com.Onestop.ecommerce.Service.UserService.UserServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class userController {

    private final UserServices services;

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
        return ResponseEntity.status(200).build();

    }
    @PostMapping("/uploadprofileicon")
public ResponseEntity<?> uploadProfileIcon(@RequestParam("email") String email,
                                           @RequestParam("image") MultipartFile image){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("username: {}", username);
        var request = ImgUploadRequest.builder()
                .email(email)
                .Image(image)
                .build();

        String response  = services.uploadProfileIcon(request);
        if(response.equals("NULL")) {
            return ResponseEntity.status(400).body("User not found");
        } else if (response.equals("INVALID_FORMAT")) {
            return ResponseEntity.status(400).body("Invalid format");

        }else if (response.equals("SUCCESS")) {
            return ResponseEntity.status(200).body("Success");
        }else{
            return ResponseEntity.status(400).body("Error");
        }


    }

    @GetMapping("/getprofileicon/{id}")
    public ResponseEntity<?> getProfileIcon(
            @PathVariable String id){
        Long imageId = Long.parseLong(id);

        ImgDownloadResponse icon = null;
        icon = services.getImage(imageId);
        log.info("file type is {}",icon.getFileType());
        return ResponseEntity.status(200)
                .contentType(MediaType.valueOf("image/png"))
                .body(new ByteArrayResource(icon.getImageData()));

    }


}
