package com.nang.reportservice.entity;

import com.nang.reportservice.enumm.ReportType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TpsReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    LocalDateTime reportTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    ReportType reportType;

    Long amount;
}
