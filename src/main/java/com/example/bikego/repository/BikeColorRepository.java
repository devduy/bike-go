package com.example.bikego.repository;

import com.example.bikego.entity.Bike.BikeColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeColorRepository extends JpaRepository<BikeColor, Long> {
    List<BikeColor> findAllByName(String name );

    BikeColor findByName(String name);
}
