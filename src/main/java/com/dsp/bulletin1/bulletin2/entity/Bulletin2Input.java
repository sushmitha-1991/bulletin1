package com.dsp.bulletin1.bulletin2.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.dsp.bulletin1.entity.Auditable;
import com.dsp.bulletin1.entity.JsonConverter;
import com.dsp.bulletin1.entity.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
	    
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name ="bulletin2_input", schema="bulletin2")
public class Bulletin2Input extends Auditable
{  
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bulletin2_input_seq")
	@SequenceGenerator(name = "bulletin2_input_seq", sequenceName = "bulletin2_input_seq", allocationSize = 1)
	private Long id; 
	@Column(name = "lok_sabha_no", nullable = false)
    private Long lokSabhaNo;

    @Column(name = "lok_sabha_session_no", nullable = false)
    private Long lokSabhaSessionNo;

    @Column(name = "rajya_sabha_session_no", nullable = false)
    private Long rajyaSabhaSessionNo;

    @Column(name = "bulletin2_no", nullable = false, length = 30)
    private String bulletin2No;

    @Column(name = "input_date", nullable = false)
    private LocalDate inputDate;

    @Column(name = "language_id", nullable = false)
    private Long languageId;

    @Column(name = "submitted_by_id", nullable = false)
    private Long submittedById;

    @Column(name = "office_id", nullable = false)
    private Long officeId;

    @Column(name = "subject", nullable = false, length = 256)
    private Long subject;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "footnote", columnDefinition = "TEXT")
    private String footnote;
	@Convert(converter = JsonConverter.class)
    @Column(name = "reference_files", columnDefinition = "JSONB")
    private List<Map<String,Object>> referenceFiles;

    @Column(name = "approver_id", nullable = false)
    private Long approverId;

    @Column(name = "approver_designation_id", nullable = false)
    private Long approverDesignationId;

    @Column(name = "draft_flag", nullable = false)
    private Boolean draftFlag;

    @Column(name = "generated_bulletin2_id")
    private Long generatedBulletin2Id;

    @Column(name = "refNo")
    private String refNo;
    @Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	// @Convert(converter = JsonConverter.class)
	// @Column(name = "attachments", columnDefinition = "jsonb")		
	// private List<Map<String, Object>> attachments;
}

	    
	    
