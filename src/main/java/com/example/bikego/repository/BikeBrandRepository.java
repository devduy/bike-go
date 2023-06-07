package com.example.bikego.repository;

import com.example.bikego.entity.Bike.BikeBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeBrandRepository extends JpaRepository<BikeBrand,Long> {
    BikeBrand findByName(String name);
}
