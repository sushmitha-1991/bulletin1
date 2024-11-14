package com.dsp.bulletin1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
    
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
// @Table(schema = "authentication") 
public class ApproverEntity {

    @Id
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_user_seq")
    // @SequenceGenerator(name = "my_user_seq", sequenceName = "my_user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username", unique = true, nullable = true)
    private String username;

    @Column(name = "role", nullable = true)
    private String role;
}

