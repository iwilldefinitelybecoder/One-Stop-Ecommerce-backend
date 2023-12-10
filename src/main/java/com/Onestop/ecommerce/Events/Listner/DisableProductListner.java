package com.Onestop.ecommerce.Events.Listner;

import com.Onestop.ecommerce.Events.Emmitter.DisableProductEmmitter;
import com.Onestop.ecommerce.Service.products.ProductsServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DisableProductListner implements ApplicationListener<DisableProductEmmitter> {

    private final ProductsServices services;
    @Override
    public void onApplicationEvent(DisableProductEmmitter disableProductEmmitter) {
        String productId = disableProductEmmitter.getProductId();
        try {
            services.removeDisabledProduct(productId);

        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
