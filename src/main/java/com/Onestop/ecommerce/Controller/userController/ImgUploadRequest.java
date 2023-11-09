package com.Onestop.ecommerce.Controller.userController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImgUploadRequest {
    private String email;
    private MultipartFile Image;
}
