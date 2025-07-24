package com.example.deviceservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.deviceservice.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    @Query("SELECT d FROM Device d WHERE d.isDel = 0 and d.isActive = 1")
    List<Device> findAllDevice();

    @Query("SELECT COUNT(d) > 0 FROM Device d WHERE d.name = :name AND d.isDel = 0 AND d.isActive = 1")
    boolean existsByName(@Param("name") String name);
}
