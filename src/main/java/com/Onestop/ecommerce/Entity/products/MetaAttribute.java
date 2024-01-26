package com.Onestop.ecommerce.Entity.products;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum MetaAttribute {
    TOP_RATED,
    BEST_SELLER,
    FEATURED,
    NEW_PRODUCT,
    ON_SALE,
    NEW_ARRIVAL,
    BIG_DISCOUNT,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    FLASH_DEAL,

    NOT_SPONSORED,
}
