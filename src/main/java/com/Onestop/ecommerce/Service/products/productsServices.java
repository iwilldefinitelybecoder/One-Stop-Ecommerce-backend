package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Dto.productsDto.productsDto;
import com.Onestop.ecommerce.Dto.productsDto.productsTagsDto;
import com.Onestop.ecommerce.Dto.productsDto.resourceDetailsTdo;
import com.Onestop.ecommerce.Entity.products.Product;
import com.Onestop.ecommerce.Entity.products.productTypeTags;
import com.Onestop.ecommerce.Entity.products.resourceDetails;
import com.Onestop.ecommerce.Repository.products.productsRepo;
import com.Onestop.ecommerce.Repository.products.resourceRepo;
import com.Onestop.ecommerce.Repository.products.tagsRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class productsServices implements productServices {


    @Autowired
    private productsRepo productsRepo;

    @Autowired
    private resourceRepo resourceRepo;

    @Autowired
    private tagsRepo tagsRepo;


    @Override
    public String saveProduct(productsDto request) {
        var product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .stock(request.getStock())
                .regularPrice(request.getRegularPrice())
                .productTypeTags(request.getProductTypeTags())
                .salePrice(request.getSalePrice() !=0?request.getSalePrice():0)
                .build();
        try {
            productsRepo.save(product);
            return product.getId().toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
    }



    @Override
    public Product findProductById(Long id) {
        return productsRepo.findById(id).get();
    }

    @Override
    public String saveImages(resourceDetailsTdo images, String ids) {
        boolean success = true; // Initialize a boolean variable to track success
        Long id = Long.parseLong(ids);
        for (MultipartFile image : images.getImage()) {
            try {
                HashMap response = SaveImageTOFs(image);
                if (response.get("originalFileName") != null && response.get("destination") != null) {
                    log.info("saving image");
                   var resource = resourceDetails.builder()
                                   .name(response.get("originalFileName").toString())
                                           .url(response.get("newFileName").toString())
                                             .product(findProductById(id))
                                                .build();

                    resourceRepo.save(resource);

                } else {
                    // Handle the case where image details are missing or invalid
                    success = false;

                }
            } catch (IOException e) {
                e.printStackTrace();
                success = false; // Set success to false in case of an exception

            }
        }

        // After the loop, return the result based on the success variable
        if (success) {
            log.info("success");
            return "success";
        } else {
            log.info("failed");
            return "failed";
        }
    }

    @Override
    public List<Product> getProducts() {
        productsRepo.findAll();
        return productsRepo.findAll();
    }

    public  HashMap SaveImageTOFs(MultipartFile image) throws IOException {
        String userHome = System.getProperty("user.home");
        String downloadsDir = userHome + "\\Downloads\\product-Images\\";
        Path directoryPath = Path.of(downloadsDir);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        try {
            String originalFileName = image.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            File destination = new File(downloadsDir + uniqueFileName);

            HashMap<String, String> response = new HashMap<>();
            response.put("originalFileName", originalFileName);
            response.put("destination", destination.getAbsolutePath());
            response.put("newFileName", uniqueFileName);
            image.transferTo(destination);

            // Return a success response
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception and return an error response
            return null;
        }
    }
}
