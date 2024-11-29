package com.dsp.bulletin1.bulletin2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.dsp.bulletin1.bulletin2.entity.Bulletin2;

@Repository
public interface Bulletin2Repository extends JpaRepository<Bulletin2, Long>, JpaSpecificationExecutor<Bulletin2> {
}