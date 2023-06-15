package com.example.bikego.repository;

import com.example.bikego.entity.OwnerShop;
import com.example.bikego.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerShopRepository extends JpaRepository<OwnerShop, Long> {
    OwnerShop findByName(String name);

    OwnerShop findByUser(User user);

    @NotNull
    @Query("SELECT o from OwnerShop o where o.user.role.id  = 2")
    List<OwnerShop> findAll();
}
