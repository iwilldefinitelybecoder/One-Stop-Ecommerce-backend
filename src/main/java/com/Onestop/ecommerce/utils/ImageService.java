package com.Onestop.ecommerce.utils;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;

import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class ImageService {

    public MultipartFile resizeImage(MultipartFile originalImageFile, int targetWidth) throws IOException, ImageWriteException, ImageReadException {
        BufferedImage originalImage = readImage(originalImageFile.getInputStream());

        // Resize the image
        BufferedImage resizedImage = resizeImage(originalImage, targetWidth);

        // Convert BufferedImage to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Get available writers
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");

        if (writers.hasNext()) {
            ImageWriter writer = writers.next();
            ImageWriteParam param = writer.getDefaultWriteParam();

            // Adjust compression quality as needed (0.95 is just an example)
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.95f);

            // Write the resized image with specified compression parameters
            writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));
            writer.write(null, new IIOImage(resizedImage, null, null), param);

            // Cleanup resources
            writer.dispose();
        }

        // Create a new MultipartFile from the resized image bytes
        return new CustomMultipartFile(byteArrayOutputStream.toByteArray(), originalImageFile.getOriginalFilename());
    }

    public BufferedImage readImage(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }


    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        double ratio = (double) targetWidth / originalWidth;
        int targetHeight = (int) (originalHeight * ratio);

        // Specify the color model explicitly
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);

        return resizedImage;
    }


    private static class CustomMultipartFile implements MultipartFile {

        private final byte[] content;
        private final String name;

        public CustomMultipartFile(byte[] content, String name) {
            this.content = content;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return name;
        }

        @Override
        public String getContentType() {
            return MediaType.IMAGE_JPEG_VALUE;
        }

        @Override
        public boolean isEmpty() {
            return content == null || content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() {
            return content;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }


        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            if (dest.exists() && !dest.delete()) {
                throw new IOException("Failed to delete existing file: " + dest.getAbsolutePath());
            }

            try (FileOutputStream outputStream = new FileOutputStream(dest)) {
                FileCopyUtils.copy(content, outputStream);
            }
        }
    }
}
