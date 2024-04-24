package com.rockbite.demo.controller;

import com.rockbite.demo.converter.MaterialConverter;
import com.rockbite.demo.converter.MaterialTypeConverter;
import com.rockbite.demo.converter.WarehouseConverter;
import com.rockbite.demo.entity.Warehouse;
import com.rockbite.demo.exception.MaterialTypeNotFoundException;
import com.rockbite.demo.model.MaterialTransferRequest;
import com.rockbite.demo.model.WarehouseDTO;
import com.rockbite.demo.service.MaterialService;
import com.rockbite.demo.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/materials")
public class MaterialController {
    private final MaterialService materialService;
    private final WarehouseService warehouseService;
    private final MaterialConverter materialConverter;
    private final MaterialTypeConverter materialTypeConverter;
    private final WarehouseConverter warehouseConverter;

    @Autowired
    public MaterialController(MaterialService materialService, WarehouseService warehouseService, MaterialConverter materialConverter, MaterialTypeConverter materialTypeConverter, WarehouseConverter warehouseConverter) {
        this.materialService = materialService;
        this.warehouseService = warehouseService;
        this.materialConverter = materialConverter;
        this.materialTypeConverter = materialTypeConverter;
        this.warehouseConverter = warehouseConverter;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMaterial(@RequestBody MaterialTransferRequest request) throws MaterialTypeNotFoundException {

        Warehouse destWarehouse = warehouseService.transfer(
                request.getSourceWarehouseId(),
                request.getDestWarehouseId(),
                request.getMaterialTypeId(),
                request.getQuantity());

        WarehouseDTO warehouseDTO = warehouseConverter.convertToModel(destWarehouse, new WarehouseDTO());
        return new ResponseEntity<>("Transferred:\n" + warehouseDTO.toString(), HttpStatus.OK);
    }

    /*@PostMapping("/add/material")
    public ResponseEntity<?> addMaterial(@RequestBody MaterialRegistrationRequest materialRegistry) throws MaterialTypeNotFoundException {
        Material material = materialService.saveMaterial(materialRegistry);
        MaterialDTO materialDTO = materialConverter.convertToModel(material, new MaterialDTO());

        return new ResponseEntity<>(materialDTO, HttpStatus.CREATED);
    }

    @PostMapping("/add/materialtype")
    public ResponseEntity<?> addMaterialType(@RequestBody MaterialType materialType) {
        MaterialType newMaterialType = materialService.createMaterialType(materialType);
        MaterialTypeDTO materialTypeDTO = materialTypeConverter.convertToModel(newMaterialType, new MaterialTypeDTO());
        return new ResponseEntity<>(materialTypeDTO, HttpStatus.CREATED);
    }*/

}
