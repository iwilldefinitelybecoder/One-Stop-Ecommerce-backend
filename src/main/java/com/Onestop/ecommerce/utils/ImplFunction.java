package com.Onestop.ecommerce.utils;

import com.Onestop.ecommerce.Entity.products.ReviewImageResource;
import com.Onestop.ecommerce.Entity.products.resourceDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImplFunction {

    public static List<String> parseImageURL(List<resourceDetails> images){
        List<String> imageURL = new ArrayList<>();
        images.forEach(image -> {
            var url = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            imageURL.add(url);
        });
        return imageURL;
    }

    public static List<String> parseImageURLs(List<ReviewImageResource> images){
        List<String> imageURL = new ArrayList<>();
        images.forEach(image -> {
            var url = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            imageURL.add(url);
        });
        return imageURL;
    }

    private static final String ORDER_ID_PREFIX = "ORD";
    private static final String TRACKING_NUMBER_PREFIX = "TN";
    private static final int RANDOM_BOUND = 1000;
    public static String generateOrderId() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(RANDOM_BOUND);
        return ORDER_ID_PREFIX + timestamp + randomNumber;
    }

    public static String generateTrackingNumber() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(RANDOM_BOUND);
        return TRACKING_NUMBER_PREFIX + timestamp + randomNumber;
    }
}
