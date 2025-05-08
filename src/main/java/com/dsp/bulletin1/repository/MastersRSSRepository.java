package com.dsp.bulletin1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsp.bulletin1.entity.MastersRajyaSabhaSession;

@Repository
public interface MastersRSSRepository extends JpaRepository<MastersRajyaSabhaSession,Long>{
    
    @Query(value = "SELECT id FROM master.rajya_sabha_session_master ORDER BY session_start DESC LIMIT 1", 
     nativeQuery = true)
    Long findLatestByCreatedBy();
}
