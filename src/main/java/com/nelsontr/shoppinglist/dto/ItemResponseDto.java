package com.nelsontr.shoppinglist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(name = "ItemResponse", description = "Item representation returned by the API")
public class ItemResponseDto {

    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Item name", example = "Milk")
    private String name;

    @Schema(description = "Unit price", example = "2.49")
    private BigDecimal price;

    @Schema(description = "Quantity to buy", example = "2")
    private Integer quantity;

    @Schema(description = "Category or aisle", example = "Dairy")
    private String category;

    @Schema(description = "Computed total cost (price * quantity)", example = "4.98", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal cost;

    @Schema(description = "Creation timestamp", example = "2025-10-05T12:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2025-10-05T12:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
}

