package com.dsp.bulletin1.entity.entitySpecification;
import org.springframework.data.jpa.domain.Specification;

import com.dsp.bulletin1.entity.Bulletin1Details;

public class Bulletin1DetailsSpec {

        public static Specification<Bulletin1Details> isActive(boolean isActive) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
        }
    }
    