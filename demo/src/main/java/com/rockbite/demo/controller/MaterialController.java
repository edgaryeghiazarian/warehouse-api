package com.rockbite.demo.controller;

import com.rockbite.demo.converter.MaterialConverter;
import com.rockbite.demo.converter.MaterialTypeConverter;
import com.rockbite.demo.entity.Material;
import com.rockbite.demo.entity.MaterialType;
import com.rockbite.demo.exception.MaterialTypeNotFoundException;
import com.rockbite.demo.model.MaterialDTO;
import com.rockbite.demo.model.MaterialRegistrationRequest;
import com.rockbite.demo.model.MaterialTypeDTO;
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

    @Autowired
    public MaterialController(MaterialService materialService, WarehouseService warehouseService, MaterialConverter materialConverter, MaterialTypeConverter materialTypeConverter) {
        this.materialService = materialService;
        this.warehouseService = warehouseService;
        this.materialConverter = materialConverter;
        this.materialTypeConverter = materialTypeConverter;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMaterial(
            @RequestParam Long sourceWarehouseId,
            @RequestParam Long destinationWarehouseId,
            @RequestParam Long materialId,
            @RequestParam int quantity) {

        warehouseService.transfer(sourceWarehouseId, destinationWarehouseId, materialId, quantity);
        return new ResponseEntity<>("Transferred", HttpStatus.OK);
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
