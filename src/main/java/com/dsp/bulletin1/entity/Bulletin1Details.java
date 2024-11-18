package com.dsp.bulletin1.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
	@Table(name ="bulletin1_details", schema="bulletin1")
	public class Bulletin1Details extends Auditable
	{  
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bulletin1_details_seq")
		@SequenceGenerator(name = "bulletin1_details_seq", sequenceName = "         bulletin1_details_seq", allocationSize = 1)
		private Long id;
		@Column(name = "bulletin_no", nullable = false)
		private Long bulletinNo;

		@Column(name = "bulletin_date", nullable = false)
		private LocalDate bulletinDate;
		
		@Column(name = "bulletin_date_hindi")
		private String bulletinDateHindi;

    	@Column(name = "lok_sabha_no")
    	private Long lokSabhaNo;
	
    	@Column(name = "lok_sabha_session_no")
    	private Long lokSabhaSessionNo; 
		
    	@Column(name = "rajya_sabha_session_no")
    	private Long rajyaSabhaSessionNo; 
	
		@Column(name = "bulletin1_content", length = 1024)
		private String bulletin1Content;

		@Column(name = "ref_no", nullable = false, unique = true)
		private String refNo;

		@Column(name = "approver_id")
		private Long approverId;
	
		@Column(name = "draft_status", nullable = false)
		private Boolean draftStatus;
		
		@Convert(converter = JsonConverter.class)
    	@Column(name = "foot_notes", columnDefinition = "jsonb")
		private List<Map<String, Object>> footNotes;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "lok_sabha_details",  referencedColumnName = "id", insertable = false, updatable = false)
		private MastersLokSabha lokSabhaDetails;
	
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "lok_sabha_session_details",  referencedColumnName = "id", insertable = false, updatable = false)
		private MastersLokSabhaSession lokSabhaSessionDetails; 
	
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "rajya_sabha_session_details",  referencedColumnName = "id", insertable = false, updatable = false)
		private MastersRajyaSabhaSession rajyaSabhaSessionDetails; 
	
		@ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "approver_details", referencedColumnName = "id", insertable = false, updatable = false)
        private BaseUser approverDetails;

		
		@PrePersist
		public void generateReferenceId() {
			this.refNo = "B1LS"+lokSabhaNo+lokSabhaSessionNo+id;
		}

}

	    
	    
