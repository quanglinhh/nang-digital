package com.nang.reportservice.repository;

import com.nang.reportservice.entity.TpsReport;
import com.nang.reportservice.enumm.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TpsRepository extends JpaRepository<TpsReport, String> {

    TpsReport findTopByReportTypeOrderByReportTimeDesc(ReportType reportType);

    @Query("SELECT t FROM TpsReport t WHERE t.reportTime >= :startTime AND t.reportTime < :endTime AND t.reportType = :type")
    List<TpsReport> findByTime(@Param("startTime") LocalDateTime startTime,
                               @Param("endTime") LocalDateTime endTime,
                               @Param("type") ReportType type);
}
