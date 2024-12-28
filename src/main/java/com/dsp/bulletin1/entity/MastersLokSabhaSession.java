package com.dsp.bulletin1.entity;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Audited
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "lok_sabha_session_masters", schema = "master")
@Entity
public class MastersLokSabhaSession extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lok_sabha_session_seq")
    @SequenceGenerator(name = "lok_sabha_session_seq", sequenceName = "lok_sabha_session_seq", allocationSize = 1)
    private Long id;

    @Column(name = "lok_sabha_master_id", nullable = false, length = 100)
    private String lokSabhaMasterId;
    @Column(name = "session_number", nullable = false, length = 100)
    private String sessionNumber;

    @ManyToOne
    @JoinColumn(name = "lok_sabha_master_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MastersLokSabha lokSabha;

}