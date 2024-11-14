package com.dsp.bulletin1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsp.bulletin1.entity.ApplicationEntity;

@Repository
public interface ApplicationDetailsRepository extends JpaRepository<ApplicationEntity, Long> {
	
}
