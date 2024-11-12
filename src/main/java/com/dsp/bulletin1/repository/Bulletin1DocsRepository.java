package com.dsp.bulletin1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsp.bulletin1.entity.Bulletin1Docs;
	     
@Repository
public interface Bulletin1DocsRepository extends JpaRepository<Bulletin1Docs, Long>{

}