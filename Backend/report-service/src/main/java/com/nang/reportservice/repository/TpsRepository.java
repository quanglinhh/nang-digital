package com.nang.reportservice.repository;

import com.nang.reportservice.entity.TpsReport;
import com.nang.reportservice.enumm.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TpsRepository extends JpaRepository<TpsReport, String> {

    TpsReport findTopByReportTypeOrderByReportTimeDesc(ReportType reportType);
}
