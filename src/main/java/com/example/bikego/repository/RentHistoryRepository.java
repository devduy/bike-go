package com.example.bikego.repository;

import com.example.bikego.common.RentStatus;
import com.example.bikego.entity.Bike.Bike;
import com.example.bikego.entity.RentHistory;
import com.example.bikego.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentHistoryRepository extends JpaRepository<RentHistory, Long> {
    RentHistory findByRentUserAndRentStatusAndBikeRent(User user, RentStatus rentStatus, Bike bike);

    RentHistory findByRentUser(User user);

    Page<RentHistory> findAllByRentUser(User user, Pageable pageable);

    @Query("SELECT r from RentHistory r where r.rentUser = :user and r.rentStatus = :rentStatus")
    Page<RentHistory> findByUserAndRentStatusPage(User user, RentStatus rentStatus,Pageable pageable);
}
