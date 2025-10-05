package com.nelsontr.shoppinglist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "ItemRequest", description = "Payload to create or update an item")
public class ItemRequestDto {

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Item name", example = "Milk", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    @Schema(description = "Unit price", example = "2.49", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @NotNull
    @Positive
    @Schema(description = "Quantity to buy", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @Size(max = 100)
    @Schema(description = "Category or aisle", example = "Dairy")
    private String category;
}

