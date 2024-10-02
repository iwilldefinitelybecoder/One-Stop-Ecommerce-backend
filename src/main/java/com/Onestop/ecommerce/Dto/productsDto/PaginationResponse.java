package com.Onestop.ecommerce.Dto.productsDto;

import lombok.*;
import org.aspectj.apache.bcel.classfile.LocalVariable;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse {
    private int totalPages;
    private Long totalElements;
    private List<?>  elements;
    private int currentPage;

}


