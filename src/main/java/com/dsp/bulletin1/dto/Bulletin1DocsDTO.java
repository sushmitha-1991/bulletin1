package com.dsp.bulletin1.dto;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class Bulletin1DocsDTO 
{
    private Long id;
    @NotEmpty
    private LocalDate bulletin1Date;
    @NotEmpty
    private LocalTime bulletin1Time;
    @NotEmpty
    private String bulletin1Type;

    private Long applicationNo;
    private Long languageId;
    private Long lokSabhaNo;
    private Long lokSabhaSessionNo;
    private Long approverNameId;
    private Long branchName;
    private Long parentId;
    @NotEmpty
    private String note;
    private String footNote;
    @NotEmpty
    private String place;
    @NotEmpty
    private String status;
    @NotEmpty
    private Long createdBy;
    private MastersLokSabhaDTO lokSabhaNoDetails;
    private MastersLokSabhaSessionDTO lokSabhaSessionNoDetails;
    private MastersBranchDTO branchNameDetails;
    private ApplicationDTO applicationName;
	}

    
    


