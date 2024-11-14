package com.dsp.bulletin1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsp.bulletin1.entity.MastersLokSabhaSession;
@Repository
public interface MasterLSSRepository extends JpaRepository<MastersLokSabhaSession,Long>{

}
