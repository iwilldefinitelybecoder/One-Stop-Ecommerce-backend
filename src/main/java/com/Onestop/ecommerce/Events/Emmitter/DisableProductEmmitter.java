package com.Onestop.ecommerce.Events.Emmitter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class DisableProductEmmitter extends ApplicationEvent {
    private String productId;

    public DisableProductEmmitter(String productId) {
        super(productId);
        this.productId = productId;
    }


}
