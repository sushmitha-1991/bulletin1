package com.dsp.bulletin1.bulletin2.entity.entitySpecification;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.dsp.bulletin1.bulletin2.entity.Bulletin2;
import com.dsp.bulletin1.entity.Bulletin1Details;
public class Bulletin2Spec {
        public static Specification<Bulletin2> isActive(boolean isActive) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
        }
          public static Specification<Bulletin2> hasLokSabhaNo(Long lokSabhaNo) {
        return (root, query, criteriaBuilder) ->
                lokSabhaNo != null ? criteriaBuilder.equal(root.get("lokSabhaNo"), lokSabhaNo) : null;
    }

    public static Specification<Bulletin2> hasDraftStatus(Boolean draftStatus) {
        return (root, query, criteriaBuilder) ->
        draftStatus != null ? criteriaBuilder.equal(root.get("draftStatus"), draftStatus) : null;
    }

    public static Specification<Bulletin2> hasDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("bulletinDate"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("bulletinDate"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("bulletinDate"), endDate);
            } else {
                return null;
            }
        };
    }
    public static Specification<Bulletin2> hasLokSabhaSessionNo(Long lokSabhaSessionNo) {
        
        return (root, query, criteriaBuilder) ->
        lokSabhaSessionNo != null ? criteriaBuilder.equal(root.get("lokSabhaSessionNo"), lokSabhaSessionNo) : null;
    }
    public static Specification<Bulletin2> hasRajyaSabhaSessionNo( Long rajyaSabhaSessionNo) {
        return (root, query, criteriaBuilder) -> 
        rajyaSabhaSessionNo != null ? criteriaBuilder.equal(root.get("rajyaSabhaSessionNo"), rajyaSabhaSessionNo) : null;
    }
}