package com.example.bikego.repository;

import com.example.bikego.entity.Bike.Bike;
import com.example.bikego.entity.Bike.BikeStatus;
import org.hibernate.annotations.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BikeRepository extends JpaRepository<Bike, Long> {
    @Query("SELECT b FROM Bike b WHERE b.deleted = false ")
    List<Bike> findAllNotDeleted();

    @Query("SELECT b FROM Bike b WHERE b.id = :id")
    @Filter(name = "notDeleted")
    Optional<Bike> findById(Long id);

    @Query("SELECT b from Bike b where b.bikeStatus = :bikeStatus and b.user.id = :uid ")
    @Filter(name = "notDeleted")
    Page<Bike> findByBikeStatus(BikeStatus bikeStatus,String uid, Pageable pageable);
    @Query("SELECT b FROM Bike b WHERE b.user.id = :userId ")
    @Filter(name = "notDeleted")
    Page<Bike> findAllByUserId(String userId, Pageable pageable);
}
