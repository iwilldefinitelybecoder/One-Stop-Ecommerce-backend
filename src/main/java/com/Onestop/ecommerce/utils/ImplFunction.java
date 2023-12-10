package com.Onestop.ecommerce.utils;

import com.Onestop.ecommerce.Entity.products.resourceDetails;

import java.util.ArrayList;
import java.util.List;

public class ImplFunction {

    public static List<String> parseImageURL(List<resourceDetails> images){
        List<String> imageURL = new ArrayList<>();
        images.forEach(image -> {
            var url = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            imageURL.add(url);
        });
        return imageURL;
    }
}
