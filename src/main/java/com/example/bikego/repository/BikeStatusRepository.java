package com.example.bikego.repository;

import com.example.bikego.entity.Bike.BikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeStatusRepository extends JpaRepository<BikeStatus, Long> {
    BikeStatus findByName(String name);
}
