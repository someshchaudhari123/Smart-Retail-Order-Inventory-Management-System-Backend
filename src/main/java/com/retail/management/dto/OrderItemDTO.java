package com.retail.management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class OrderItemDTO {

    @NotNull
    private Long productId;

    @Min(1)
    private int quantity;
}
