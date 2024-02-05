package com.Onestop.ecommerce.Entity.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResourceInfo {
    private String originalFileName;
    private String newFileName;
    private String downSizedFileName;
}
