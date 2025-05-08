package com.dsp.bulletin1.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.envers.Audited;

import com.dsp.bulletin1.entity.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@SuperBuilder
@Table(name = "bulletin1_details", schema = "bulletin1")
public class Bulletin1Details extends ApplicationInstance {
	@Column(name = "bulletin_no", nullable = false)
	private Long bulletinNo;

	@Column(name = "bulletin_date", nullable = false)
	// private LocalDateTime bulletinDate;
	private LocalDateTime bulletinDate;

	@Column(name = "bulletin_date_hindi")
	private String bulletinDateHindi;

	@Column(name = "lok_sabha_no")
	private Long lokSabhaNo;

	@Column(name = "lok_sabha_session_no")
	private Long lokSabhaSessionNo;

	@Column(name = "rajya_sabha_session_no")
	private Long rajyaSabhaSessionNo;

	@Column(name = "bulletin1_content", columnDefinition = "text")
	private String bulletin1Content;

	@Column(name = "ref_no", nullable = false, unique = true)
	private String refNo;

	@Column(name = "approver_id")
	private Long approverId;

	@Column(name = "draft_status", nullable = false)
	private Boolean draftStatus;

	@Column(name = "status")
	private String status;

	// @Column(name = "starting_page_no")
	// private Long startingPageNo;

	@Convert(converter = JsonConverter.class)
	@Column(name = "foot_notes", columnDefinition = "jsonb")
	private List<Map<String, Object>> footNotes;

	@Convert(converter = JsonConverter.class)
	@Column(name = "documents", columnDefinition = "jsonb")
	private List<Map<String, Object>> documents;

	@ManyToOne
	@JoinColumn(name = "lok_sabha_session_no", referencedColumnName = "id", insertable = false, updatable = false)
	private MastersLokSabhaSession lokSabhaSessionDetails;
	@ManyToOne
	@JoinColumn(name = "rajya_sabha_session_no", referencedColumnName = "id", insertable = false, updatable = false)
	private MastersRajyaSabhaSession rajyaSabhaSessionDetails;
	@ManyToOne
	@JoinColumn(name = "approver_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseUser approverDetails;
}
