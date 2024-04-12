package com.rockbite.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

@Entity
@Table(name = "material_type")
@Getter
@Setter
@AllArgsConstructor
public class MaterialType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String icon;
    private int maxCapacity;


    public MaterialType() {

    }
}
