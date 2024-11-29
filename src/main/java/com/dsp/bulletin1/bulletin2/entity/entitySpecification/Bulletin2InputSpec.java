package com.dsp.bulletin1.bulletin2.entity.entitySpecification;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.dsp.bulletin1.bulletin2.entity.Bulletin2Input;
import com.dsp.bulletin1.entity.Bulletin1Details;
public class Bulletin2InputSpec {
        public static Specification<Bulletin2Input> isActive(boolean isActive) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
        }
          public static Specification<Bulletin2Input> hasLokSabhaNo(Long lokSabhaNo) {
        return (root, query, criteriaBuilder) ->
                lokSabhaNo != null ? criteriaBuilder.equal(root.get("lokSabhaNo"), lokSabhaNo) : null;
    }

    public static Specification<Bulletin2Input> hasSaveAsDraft(Boolean draftFlag) {
        return (root, query, criteriaBuilder) ->
        draftFlag != null ? criteriaBuilder.equal(root.get("draftFlag"), draftFlag) : null;
    }

    public static Specification<Bulletin2Input> hasDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("inputDate"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("inputDate"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("inputDate"), endDate);
            } else {
                return null;
            }
        };
    }
    public static Specification<Bulletin2Input> hasLokSabhaSessionNo(Long lokSabhaSessionNo) {
        
        return (root, query, criteriaBuilder) ->
        lokSabhaSessionNo != null ? criteriaBuilder.equal(root.get("lokSabhaSessionNo"), lokSabhaSessionNo) : null;
    }
    public static Specification<Bulletin2Input> hasRajyaSabhaSessionNo( Long rajyaSabhaSessionNo) {
        return (root, query, criteriaBuilder) -> 
        rajyaSabhaSessionNo != null ? criteriaBuilder.equal(root.get("rajyaSabhaSessionNo"), rajyaSabhaSessionNo) : null;
    }
    //bulletin2_no
    public static Specification<Bulletin2Input> hasBulletin2No( Long bulletin2No) {
        return (root, query, criteriaBuilder) -> 
        bulletin2No != null ? criteriaBuilder.equal(root.get("bulletin2No"), bulletin2No) : null;
    }
}