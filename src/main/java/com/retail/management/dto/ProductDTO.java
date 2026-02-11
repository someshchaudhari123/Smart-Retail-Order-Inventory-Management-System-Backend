package com.retail.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private String name;
    private double price;
    private int stockQty;

    // MUST EXIST
    private Long categoryId;
}
