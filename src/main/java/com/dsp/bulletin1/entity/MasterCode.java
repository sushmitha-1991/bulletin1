// package com.dsp.bulletin1.entity;
//     import org.hibernate.annotations.Immutable;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
    
//     @Entity
//     @Data
//     // @Immutable
//     @NoArgsConstructor
//     @AllArgsConstructor
//     @Builder
//     @Table(name ="codes",schema = "master")
//     public class MasterCode extends Auditable {
    
//         @Id
//         @GeneratedValue(strategy = GenerationType.IDENTITY)
//         private Long id;
    
    
//         @Column(name = "abbr")
//         private String abbr;
    
//         @Column(name = "code")
//         private String code;
    
    
//         @Column(name = "description")
//         private String description;
    
    
//     }
    
