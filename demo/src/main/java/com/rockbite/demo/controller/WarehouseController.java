package com.rockbite.demo.controller;


import com.rockbite.demo.converter.WarehouseConverter;
import com.rockbite.demo.entity.Warehouse;
import com.rockbite.demo.exception.MaterialTypeNotFoundException;
import com.rockbite.demo.model.WarehouseDTO;
import com.rockbite.demo.model.WarehouseMaterialRequest;
import com.rockbite.demo.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/warehouse")
@Validated
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final WarehouseConverter warehouseConverter;

    @Autowired
    public WarehouseController(WarehouseService warehouseService, WarehouseConverter warehouseConverter) {
        this.warehouseService = warehouseService;
        this.warehouseConverter = warehouseConverter;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWarehouse(@RequestParam String name) {
        Warehouse warehouse = warehouseService.createWarehouse(name);
        return new ResponseEntity<>("Warehouse created with name: " + warehouse.getName(), HttpStatus.CREATED);
    }

    @GetMapping("/get/{warehouseId}")
    public ResponseEntity<?> getWarehouse(@PathVariable long warehouseId) {
        Warehouse warehouse = warehouseService.getWarehouse(warehouseId);
        WarehouseDTO warehouseDTO = warehouseConverter.convertToModel(warehouse, new WarehouseDTO());
        return new ResponseEntity<>(warehouseDTO.toString(), HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<?> addMaterialToWarehouse(@Valid @RequestBody WarehouseMaterialRequest request) throws MaterialTypeNotFoundException {
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        if (warehouseService.hasMaterial(request.getWarehouseId(), request.getMaterialTypeId())) {
            Warehouse warehouse = warehouseService.addMaterialQuantityToWarehouse(request.getWarehouseId(), request.getMaterialTypeId(), request.getQuantity());
            warehouseDTO = warehouseConverter.convertToModel(warehouse, warehouseDTO);
        } else {
            Warehouse warehouse = warehouseService.addNewMaterialToWarehouse(request.getWarehouseId(), request.getMaterialTypeId(), request.getQuantity());
            warehouseDTO = warehouseConverter.convertToModel(warehouse, warehouseDTO);
        }
        return new ResponseEntity<>("Material is added to warehouse: " + warehouseDTO.toString(), HttpStatus.OK);
    }

    @PutMapping("/remove")
    public ResponseEntity<?> removeMaterialFromWarehouse(@Valid @RequestBody WarehouseMaterialRequest request) throws MaterialTypeNotFoundException {
        Warehouse warehouse = warehouseService.removeMaterialFromWarehouse(request.getWarehouseId(), request.getMaterialTypeId(), request.getQuantity());
        WarehouseDTO warehouseDTO = warehouseConverter.convertToModel(warehouse, new WarehouseDTO());

        return new ResponseEntity<>("Material is removed from warehouse: " + warehouseDTO.toString(), HttpStatus.OK);
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
