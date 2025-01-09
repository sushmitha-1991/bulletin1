package com.dsp.bulletin1.entity.specification;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.dsp.bulletin1.entity.Bulletin1Details;
public class Bulletin1Spec 
{
    public static Specification<Bulletin1Details> isActive(boolean isActive) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
    }
    public static Specification<Bulletin1Details> hasLokSabhaNo(Long lokSabhaNo) 
    {
        return (root, query, criteriaBuilder) ->
            lokSabhaNo != null ? criteriaBuilder.equal(root.get("lokSabhaNo"), lokSabhaNo) : null;
    }
    public static Specification<Bulletin1Details> hasDraftStatus(Boolean draftStatus) {
        return (root, query, criteriaBuilder) ->
                draftStatus != null ? criteriaBuilder.equal(root.get("draftStatus"), draftStatus) : null;
    }
    public static Specification<Bulletin1Details> hasDateBetween(LocalDate startDate, LocalDate endDate) {
    return (root, query, criteriaBuilder) -> {
        if (startDate != null && endDate != null) {
            // Convert LocalDate to SQL Date using criteriaBuilder.function
            return criteriaBuilder.between(
                criteriaBuilder.function("DATE", Date.class, root.get("bulletinDate")),
                java.sql.Date.valueOf(startDate),
                java.sql.Date.valueOf(endDate)
            );
        } else if (startDate != null) {
            return criteriaBuilder.greaterThanOrEqualTo(
                criteriaBuilder.function("DATE", Date.class, root.get("bulletinDate")),
                java.sql.Date.valueOf(startDate)
            );
        } else if (endDate != null) {
            return criteriaBuilder.lessThanOrEqualTo(
                criteriaBuilder.function("DATE", Date.class, root.get("bulletinDate")),
                java.sql.Date.valueOf(endDate)
            );
        }
        return null; // If no startDate or endDate, return null (no filtering)
    };
}
    // public static Specification<Bulletin1Details> hasDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
    // //    Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    //    Date sdate = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
    //    Date edate = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

    //     return (root, query, criteriaBuilder) -> 
    //     {
    //         if (startDate != null && endDate != null) {
    //             return criteriaBuilder.between(root.get("bulletinDate"), startDate, endDate);
    //         } else if (startDate != null) {
    //             return criteriaBuilder.greaterThanOrEqualTo(root.get("bulletinDate"), startDate);
    //         } else if (endDate != null) {
    //             return criteriaBuilder.lessThanOrEqualTo(root.get("bulletinDate"), endDate);
    //         } else {
    //             return null;
    //         }
    //     };
    // }
    public static Specification<Bulletin1Details> hasLokSabhaSessionNo(Long lokSabhaSessionNo) 
    {        
        return (root, query, criteriaBuilder) ->
        lokSabhaSessionNo != null ? criteriaBuilder.equal(root.get("lokSabhaSessionNo"), lokSabhaSessionNo) : null;
    }
    public static Specification<Bulletin1Details> hasRajyaSabhaSessionNo( Long rajyaSabhaSessionNo) {
        return (root, query, criteriaBuilder) -> 
        rajyaSabhaSessionNo != null ? criteriaBuilder.equal(root.get("rajyaSabhaSessionNo"), rajyaSabhaSessionNo) : null;
    }
    public static Specification<Bulletin1Details> hasStatus( String status) {
        return (root, query, criteriaBuilder) -> 
        status != null ? criteriaBuilder.equal(root.get("status"), status) : null;
    }
}
    
