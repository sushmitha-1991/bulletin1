package com.dsp.bulletin1.dto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.dsp.bulletin1.entity.enums.Status;

import jakarta.validation.constraints.NotEmpty;
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
public class Bulletin1DetailsDTO {
    @NotEmpty
    private Long bulletinNo;
	@NotEmpty
    private LocalDateTime bulletinDate;
    private String bulletinDateHindi;
    private Long lokSabhaNo;

    private Long lokSabhaSessionNo;
    private Long rajyaSabhaSessionNo;
    @NotEmpty
    private String bulletin1Content;
    @NotEmpty
    private String refNo;
    private Long approverId;
    private  Long startingPageNo;
    // private Long codeId;  
	@NotEmpty
    private Boolean draftStatus;
    private Status status;
    private List<Map<String, Object>> footNotes;
    private List<Map<String, Object>> documents;

    private MastersLokSabhaDTO lokSabhaDetails;
    private MastersRajyaSabhaSessionDTO rajyaSabhaSessionDetails;
    private MastersLokSabhaSessionDTO lokSabhaSessionDetails;
    private ApproverDTO approverDetails;

	//Application Instances
    private Long id;
    private Long applicationId; // Reference to Application ID
    private String applicationNo;
    private String currentStatus;
    private Long refId;
    private ApplicationInstanceDTO applicationInstanceDTO;
	}