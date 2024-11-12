package com.dsp.bulletin1.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Long id;
    @NotEmpty
    private Long bulletinNo;
	@NotEmpty
    private LocalDate bulletinDate;
    private String bulletinDateHindi;
    private Long lokSabhaNo;

    private Long lokSabhaSessionNo;
    private Long rajyaSabhaSessionNo;
    @NotEmpty
    private String bulletin1Content;
    @NotEmpty
    private String refNo;
    private Long approverId;
	@NotEmpty
    private Boolean draftStatus;
    private MastersLokSabhaDTO lokSabhaDetails;
    private MastersRajyaSabhaSessionDTO rajyaSabhaSessionDetails;
    private MastersLokSabhaSessionDTO lokSabhaSessionDetails;
    private ApproverDTO approverDetails;
	
	}

    
    


