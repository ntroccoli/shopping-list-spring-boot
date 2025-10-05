package com.nelsontr.shoppinglist.controller;

import com.nelsontr.shoppinglist.dto.ItemRequestDto;
import com.nelsontr.shoppinglist.dto.ItemResponseDto;
import com.nelsontr.shoppinglist.exception.ItemNotFoundException;
import com.nelsontr.shoppinglist.exception.ApiError;
import com.nelsontr.shoppinglist.mapper.ItemMapper;
import com.nelsontr.shoppinglist.model.Item;
import com.nelsontr.shoppinglist.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Tag(name = "Items", description = "Operations for managing shopping list items")
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @GetMapping
    @Operation(summary = "List all items", description = "Returns all items currently in the shopping list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ItemResponseDto.class))))
    })
    public List<ItemResponseDto> getAllItems() {
        return itemService.findAll().stream()
                .map(itemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an item by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ItemResponseDto getItemById(@Parameter(description = "Item id", example = "1") @PathVariable Long id) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        return itemMapper.toResponse(item);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new item")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ItemResponseDto createItem(@Valid @RequestBody ItemRequestDto request) {
        Item toSave = itemMapper.toEntity(request);
        Item saved = itemService.save(toSave);
        return itemMapper.toResponse(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ItemResponseDto updateItem(@Parameter(description = "Item id", example = "1") @PathVariable Long id,
                                      @Valid @RequestBody ItemRequestDto request) {
        Item saved = itemService.update(id, request);
        return itemMapper.toResponse(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an item by id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Item deleted (no content)"),
        @ApiResponse(responseCode = "404", description = "Item not found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public void deleteItem(@Parameter(description = "Item id", example = "1") @PathVariable Long id) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        itemService.deleteById(id);
    }
}
