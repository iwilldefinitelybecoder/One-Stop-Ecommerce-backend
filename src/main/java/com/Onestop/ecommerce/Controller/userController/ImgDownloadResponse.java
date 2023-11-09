package com.Onestop.ecommerce.Controller.userController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgDownloadResponse {
    private String fileName;
    private byte[] imageData;
    private String fileType;
    private long size;
}
