package com.example.bikego.repository;

import com.example.bikego.entity.OwnerShop;
import com.example.bikego.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerShopRepository extends JpaRepository<OwnerShop, Long> {
    OwnerShop findByName(String name);

    OwnerShop findByUser(User user);
}
