package com.rockbite.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialType that = (MaterialType) o;
        return maxCapacity == that.maxCapacity && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, icon, maxCapacity);
    }
}
