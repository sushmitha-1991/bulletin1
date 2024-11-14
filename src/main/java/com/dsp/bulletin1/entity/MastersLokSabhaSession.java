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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
// @Immutable
@Table(name ="lok_sabha_session_masters",schema = "master")
@Entity
public class MastersLokSabhaSession extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lok_sabha_session_seq")
    @SequenceGenerator(name = "lok_sabha_session_seq", sequenceName = "lok_sabha_session_seq", allocationSize = 1)
    private Long id;


    @Column(name = "session_number", nullable = false, length = 100)
    private String sessionNumber;
    
}