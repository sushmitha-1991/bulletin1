package com.dsp.bulletin1.bulletin2.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.dsp.bulletin1.dto.ApplicationInstanceDTO;
import com.dsp.bulletin1.dto.MastersBranchDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaSessionDTO;
import com.dsp.bulletin1.dto.MastersRajyaSabhaSessionDTO;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Builder 
public class Bulletin2DTO {
    @NotEmpty
    private LocalDate lobDate;
    @NotEmpty
    private LocalTime lobTime;
    @NotEmpty
    private Long lobType;
    private Long lokSabhaNo;
    private Long lokSabhaSessionNo;
    private Long rajyaSabhaSessionNo;
    private Long approverNameId;
    private Long parentId;
    @NotEmpty
    private String note;
    private String footNote;
    @NotEmpty
    private String place;
    @NotEmpty
    private Status status;
    @NotEmpty
	private Boolean draftStatus;
    private MastersLokSabhaDTO lokSabhaNoDetails;
    private MastersLokSabhaSessionDTO lokSabhaSessionNoDetails;
    private MastersRajyaSabhaSessionDTO rajyaSabhaSessionNoDetails;
    private MastersBranchDTO branchNameDetails;
    //Application Instances
    
private Long id;
private Long applicationId; // Reference to Application ID
private String applicationNo;
private String currentStatus;
    private ApplicationInstanceDTO applicationInstanceDTO;
}
