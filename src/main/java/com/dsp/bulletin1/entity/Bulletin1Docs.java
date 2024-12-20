package com.dsp.bulletin1.entity;

	import java.time.LocalDate;
//import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
	 
	@Entity
	@Data
	@EqualsAndHashCode(callSuper = false)
	@Setter
	@Getter
	@Table(name ="bulletin1_ref_docs", schema="bulletin1")
	public class Bulletin1Docs
	{  
		@Id	
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bulletin1_doc_seq")
		@SequenceGenerator(name = "bulletin1_doc_seq", sequenceName = "bulletin1.bulletin1_doc_seq", allocationSize = 1)
	    private Long id;
		@Column(name = "ref_doc_date", nullable = false)
		private LocalDate refDocDate;
		@Column(name = "ref_doc_id",nullable = false)
        private Long refDocId;
        @Column(name = "bulletin1_id", nullable = false)
        private Long bulletin1Id;
		// @ManyToOne(fetch = FetchType.LAZY)
		// @JoinColumn(name = "master_codes",  referencedColumnName = "id", insertable = false, updatable = false)
		// private MasterCode codes;
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "bulletin1_details",  referencedColumnName = "id", insertable = false, updatable = false)
		private Bulletin1Details bulletin1Details;
}

	 
	 

