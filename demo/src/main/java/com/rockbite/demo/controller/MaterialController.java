package com.rockbite.demo.controller;

import com.rockbite.demo.converter.WarehouseConverter;
import com.rockbite.demo.entity.Warehouse;
import com.rockbite.demo.exception.MaterialTypeNotFoundException;
import com.rockbite.demo.model.MaterialTransferRequest;
import com.rockbite.demo.model.WarehouseDTO;
import com.rockbite.demo.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/materials")
public class MaterialController {
    private final WarehouseService warehouseService;
    private final WarehouseConverter warehouseConverter;

    @Autowired
    public MaterialController(WarehouseService warehouseService, WarehouseConverter warehouseConverter) {
        this.warehouseService = warehouseService;
        this.warehouseConverter = warehouseConverter;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMaterial(@Valid @RequestBody MaterialTransferRequest request) throws MaterialTypeNotFoundException {

        Warehouse destWarehouse = warehouseService.transfer(
                request.getSourceWarehouseId(),
                request.getDestWarehouseId(),
                request.getMaterialTypeId(),
                request.getQuantity());

        WarehouseDTO warehouseDTO = warehouseConverter.convertToModel(destWarehouse, new WarehouseDTO());
        return new ResponseEntity<>("Transferred:\n" + warehouseDTO.toString(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
