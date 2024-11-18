package com.dsp.bulletin1.repository;


import java.util.List;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsp.bulletin1.entity.Bulletin1Details;
@Repository

public interface Bulletin1DetailsRepository extends JpaRepository<Bulletin1Details, Long> ,  JpaSpecificationExecutor<Bulletin1Details> {

			
}
