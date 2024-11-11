// package com.dsp.bulletin1.entity;

// 	import java.time.LocalDate;
// //import com.fasterxml.jackson.databind.JsonNode;
// import java.time.LocalTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.SequenceGenerator;
// import jakarta.persistence.Table;
// import lombok.Data;
// import lombok.EqualsAndHashCode;
// import lombok.Getter;
// import lombok.Setter;
	 
// 	@Entity
// 	@Data
// 	@EqualsAndHashCode(callSuper = false)
// 	@Setter
// 	@Getter
// 	@Table(name ="bulletin1_ref_docs", schema="bulletin1")
// 	public class Bulletin1Docs extends Auditable
// 	{  
// 		@Id	
// 		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bulletin1_seq")
// 		@SequenceGenerator(name = "bulletin1_seq", sequenceName = "bulletin1_seq", allocationSize = 1)
// 	private Long id;
// 		@Column(name = "bulletin1_date", nullable = false)
// 		private LocalDate bulletin1Date;
// 		@Column(name = "bulletin1_time", nullable = false)
// 		private LocalTime bulletin1Time;
// 		@Column(name = "bulletin1_type", nullable = false)
// 		private String bulletin1Type;
// 		@Column(name = "application_no")
// 		private Long applicationNo;
// 		@Column(name = "language_id")
// 		private Long languageId;
//     	@Column(name = "lok_sabha_no")
//     	private Long lokSabhaNo;
//     	@Column(name = "lok_sabha_session_no")
//     	private Long lokSabhaSessionNo;
// 		@Column(name = "approver_name_id")
// 		private Long approverNameId;
// 		@Column(name = "branch_name_id")
// 		private Long branchName;

// 		@Column(name = "parent_id")
// 		private Long parentId;

// 		@Column(name = "note", length=5120, nullable = false)
// 		private String note;
	
// 		@Column(name = "status", nullable=false)
// 		private String status;
		
// 		@Column(name = "foot_note")
// 		private String footNote;
		
// 		@Column(name = "place",nullable = false)
// 		private String place;	
		
// 		@ManyToOne(fetch = FetchType.LAZY)
// 		@JoinColumn(name = "lok_sabha_details",  referencedColumnName = "id", insertable = false, updatable = false)
// 		private MastersLokSabha lokSabhaNoDetails;
	
// 		@ManyToOne(fetch = FetchType.LAZY)
// 		@JoinColumn(name = "lok_sabha_session_details",  referencedColumnName = "id", insertable = false, updatable = false)
// 		private MastersLokSabhaSession lokSabhaSessionNoDetails; 
	
// 		@ManyToOne(fetch = FetchType.LAZY)
// 		@JoinColumn(name = "branch_name_details",  referencedColumnName = "id", insertable = false, updatable = false)
// 		private MastersBranch branchNameDetails;

// 		@ManyToOne(fetch = FetchType.LAZY)
// 		@JoinColumn(name = "application_details",  referencedColumnName = "id", insertable = false, updatable = false)
// 		private ApplicationEntity applicationName;
// }

	 
	 

