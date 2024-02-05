package com.Onestop.ecommerce.utils;

import com.Onestop.ecommerce.Entity.products.ReviewImageResource;
import com.Onestop.ecommerce.Entity.products.resourceDetails;

import java.util.*;

public class ImplFunction {

    public static List<String> parseImageURL(List<resourceDetails> images){
        List<String> imageURL = new ArrayList<>();
        images.forEach(image -> {
            var url = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            imageURL.add(url);
        });
        return imageURL;
    }

    public static List<Map<String,String>> pareImageURLToMap(List<resourceDetails> images){
        List<Map<String,String>> imageUrl = new ArrayList<>();
        images.forEach(image->{
            Map<String,String> images1 =new HashMap<>();
            var downSizedImg = "http://localhost:8000/image-resources/product-Images/" + image.getDownSizedUrl();
            var normalImage = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            images1.put("imageURL",normalImage);
            images1.put("imagePreview",downSizedImg);
            imageUrl.add(images1);
        });
        return imageUrl;
    }

    public static Map<String,String> pareImageURLToMap(resourceDetails images){

            Map<String,String> images1 =new HashMap<>();
            var downSizedImg = "http://localhost:8000/image-resources/product-Images/" + images.getDownSizedUrl();
            var normalImage = "http://localhost:8000/image-resources/product-Images/" + images.getUrl();
            images1.put("imageURL",normalImage);
            images1.put("imagePreview",downSizedImg);

        return images1;
    }

    public static String parsePreviewImageURL(resourceDetails images){
        return  "http://localhost:8000/image-resources/product-Images/" + images.getDownSizedUrl();
    }
    public static String parseImageURL(resourceDetails images){

            return  "http://localhost:8000/image-resources/product-Images/" + images.getUrl();
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
