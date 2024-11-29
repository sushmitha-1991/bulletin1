package com.dsp.bulletin1.bulletin2.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.dsp.bulletin1.dto.MasterCodeDTO;
import com.dsp.bulletin1.dto.MastersBranchDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaSessionDTO;
import com.dsp.bulletin1.dto.MastersRajyaSabhaSessionDTO;
import com.dsp.bulletin1.dto.UserDetailsDTO;
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
public class Bulletin2InputDTO
 {
    @NotEmpty
	private Long id;
    private Long lokSabhaNo;
    private Long lokSabhaSessionNo;
    private Long rajyaSabhaSessionNo;
    private String bulletin2No;
    private LocalDate inputDate;
    private Long languageId;
    private Long submittedById;
    private Long officeId;
    private Long subject;
    private String content;
    private String footnote;
    private List<Map<String,Object>> referenceFiles;
    private Long approverId;
    private Long approverDesignationId;
    private Boolean draftFlag;
    private Long generatedBulletin2Id;
    private String refNo;
    private Status status;
    private MastersLokSabhaDTO lokSabhaDetails;
    private MastersLokSabhaSessionDTO lokSabhaSessionDetails;
    private MastersRajyaSabhaSessionDTO rajyaSabhaSessionDetails;
    private MastersBranchDTO officeDetails;
    private MasterCodeDTO subjectDetails;
    private UserDetailsDTO submittedByDetails;
    private UserDetailsDTO approverDetails;
    private Bulletin2DTO bulletin2Details;

}
