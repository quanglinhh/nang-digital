package com.nang.orderservice.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nang.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT o FROM Order o WHERE o.deviceId = :deviceId AND o.isDel = 0 AND o.isActive = 1 ORDER BY o.startTime DESC ")
    List<Order> findByDeviceId(@Param("deviceId") String deviceId);

	@Query("SELECT o FROM Order o WHERE o.deviceId = :deviceId AND o.startTime >= :startDate AND  o.startTime <= :endDate " +
			"AND o.isDel = 0 AND o.isActive = 1 ORDER BY o.startTime DESC ")
	List<Order> findByDeviceIdAndDate(@Param("deviceId") String deviceId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
			SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END
			FROM Order o
			WHERE o.deviceId = :deviceId
			AND o.isDel = 0
			AND o.isActive = 1
			AND (
				:startTime <= o.endTime AND :endTime >= o.startTime
			)
			""")
    boolean isDuplicateTime(
            @Param("deviceId") String deviceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

	@Query("SELECT o.id FROM Order o WHERE o.deviceId = :deviceId ORDER BY o.startTime DESC ")
	List<String> getOrderIdsByDeviceId(@Param("deviceId") String deviceId);

	@Query("SELECT o FROM Order o WHERE o.deviceId = :deviceId ORDER BY o.startTime DESC ")
	List<Order> getByDeviceId(@Param("deviceId") String deviceId);
}
