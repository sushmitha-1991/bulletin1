package com.dsp.bulletin1.bulletin2.entity;


import java.time.LocalDate;
import java.time.LocalTime;

import com.dsp.bulletin1.dto.ApplicationInstanceDTO;
import com.dsp.bulletin1.entity.ApplicationInstance;
import com.dsp.bulletin1.entity.Auditable;
import com.dsp.bulletin1.entity.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
	 
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
@SuperBuilder
@Table(name ="bulletin2", schema="bulletin2")
public class Bulletin2 extends ApplicationInstance
{  
	@Column(name = "lob_date", nullable = false)
	private LocalDate lobDate;
	@Column(name = "lob_time", nullable = false)
	private LocalTime lobTime;
	@Column(name = "lob_type")
	private Long lobType;
	@Column(name = "lok_sabha_no")
	private Long lokSabhaNo;
	@Column(name = "lok_sabha_session_no")
	private Long lokSabhaSessionNo;
	@Column(name = "rajya_sabha_session_no")
	private Long rajyaSabhaSessionNo;
	@Column(name = "approver_name_id")
	private Long approverNameId;

	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "note", length=5120, nullable = false)
	private String note;

	@Column(name = "foot_note")
	private String footNote;

	@Column(name = "place",nullable = false)
	private String place;	
	@Column(name="draft_status",nullable = false)
	private Boolean draftStatus;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
}

	 
	 

