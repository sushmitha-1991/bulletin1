package com.dsp.bulletin1.bulletin2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.dsp.bulletin1.bulletin2.entity.Bulletin2Input;

@Repository
public interface Bulletin2InputRepository extends JpaRepository<Bulletin2Input, Long>, JpaSpecificationExecutor<Bulletin2Input> {
}