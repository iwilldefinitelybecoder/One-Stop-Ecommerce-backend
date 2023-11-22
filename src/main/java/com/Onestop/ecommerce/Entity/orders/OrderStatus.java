package com.Onestop.ecommerce.Entity.orders;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



public enum OrderStatus {
    ORDERED,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED,
    REFUNDED,
    EXCHANGED,
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED,
    DECLINED,
    ABANDONED,
    CART,
    WISHLIST,
    SAVED,
    DRAFT,
    ARCHIVED,
    DELETED,
    UNKNOWN;

}
