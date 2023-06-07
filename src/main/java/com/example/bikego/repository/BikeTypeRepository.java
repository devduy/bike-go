package com.example.bikego.repository;

import com.example.bikego.entity.Bike.BikeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeTypeRepository extends JpaRepository<BikeType, Long> {
    BikeType findByName(String name);
}
