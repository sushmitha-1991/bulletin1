package com.dsp.bulletin1.entity;
import java.time.LocalDate;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
    
    @Data
    @Setter
    // @Immutable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Table(name ="lok_sabha_masters",schema = "master")
    @Entity
    public class MastersLokSabha extends Auditable {
    
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lok_sabha_seq")
        @SequenceGenerator(name = "lok_sabha_seq", sequenceName = "lok_sabha_seq", allocationSize = 1)
        private Long id;

        // @Column(name = "speaker", length = 255)
        // private String speaker;

        // @Column(name = "deputy_speaker", length = 255)
        // private String deputySpeaker;

        @Column(name = "lok_sabha_number", nullable = false, length = 50)
        private String lokSabhaNumber;

        // @Column(name = "start_date")
        // private LocalDate startDate;
        
        // @Column(name = "end_date")
        // private LocalDate endDate;

        // @Column(name = "total_sessions")
        // private Long totalSessions;

        // @Column(name = "total_sittings")
        // private Long totalSittings;
    }
   
