package com.nang.paymentservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nang.paymentservice.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("SELECT p FROM Payment p WHERE p.orderId = :orderId AND p.isActive = 1 AND p.isDel = 0 ORDER BY p.rentTime DESC, p.createdAt DESC")
    List<Payment> findPaymentsByOrderId(@Param("orderId") String orderId);

    @Query("SELECT p FROM Payment p " + "WHERE p.status = :status "
            + "AND p.rentTime >= :startDateTime "
            + "AND p.rentTime < :endDateTime "
            + "ORDER BY p.rentTime ASC")
    List<Payment> findPaymentsByStatusAndDate(
            @Param("status") String status,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);


    @Query("SELECT p FROM Payment p WHERE p.orderId in :orderIds AND p.status = :status AND p.isActive = 1 AND p.isDel = 0")
    List<Payment> findByOrderIdsAndStatus(@Param("orderIds") List<String> orderIds, @Param("status") Integer status);
}
