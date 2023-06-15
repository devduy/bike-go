package com.example.bikego.repository;

import com.example.bikego.entity.Bike.BikeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeImageRepository extends JpaRepository<BikeImage, Long> {
}
