package com.dsp.bulletin1.mapper;
import org.mapstruct.Mapper;

import com.dsp.bulletin1.dto.ApplicationInstanceDTO;
import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.entity.Bulletin1Details;
@Mapper(componentModel = "string")
public  interface Bulletin1DetailsMapper {
    // Convert Entity to DTO
    public static Bulletin1DetailsDTO toDTO(Bulletin1Details entity) 
    {
        return Bulletin1DetailsDTO.builder()
        .id(entity.getId())
        .bulletinNo(entity.getBulletinNo())
        .bulletinDate(entity.getBulletinDate())
        .bulletinDateHindi(entity.getBulletinDateHindi())
        .approverId(entity.getApproverId())
        .refNo(entity.getRefNo())
        .startingPageNo(entity.getStartingPageNo())
        .bulletin1Content(entity.getBulletin1Content())
        .draftStatus(entity.getDraftStatus())
        .status(entity.getStatus())
        .codeId(entity.getCodeId())
        .footNotes(entity.getFootNotes())
        .documents(entity.getDocuments())
        .lokSabhaNo(entity.getLokSabhaNo())
        .lokSabhaSessionNo(entity.getLokSabhaSessionNo())
        .rajyaSabhaSessionNo(entity.getRajyaSabhaSessionNo())
        .applicationId(entity.getApplicationId())
        .applicationNo(entity.getApplicationNo())
        .currentStatus(entity.getCurrentStatus())
        .applicationInstanceDTO(ApplicationInstanceDTO.builder()
        .id(entity.getId())
        .applicationId(entity.getApplicationId())
        .applicationNo(entity.getApplicationNo())
        .currentStatus(entity.getCurrentStatus())
        .build())
        .build(); 
    }
    // Convert DTO to Entity
    public static Bulletin1Details toEntity(Bulletin1DetailsDTO dto) {
        return Bulletin1Details.builder()
        .id(dto.getId())
        .bulletinNo(dto.getBulletinNo())
        .bulletinDate(dto.getBulletinDate())
        .bulletinDateHindi(dto.getBulletinDateHindi())
        .approverId(dto.getApproverId())
        .bulletin1Content(dto.getBulletin1Content())
        .codeId(dto.getCodeId())
        .refNo(dto.getRefNo())
        .startingPageNo(dto.getStartingPageNo())
        .draftStatus(dto.getDraftStatus())
        .status(dto.getStatus())
        .footNotes(dto.getFootNotes())
        .documents(dto.getDocuments())
        .lokSabhaNo(dto.getLokSabhaNo())
        .lokSabhaSessionNo(dto.getLokSabhaSessionNo())
        .rajyaSabhaSessionNo(dto.getRajyaSabhaSessionNo())
        .applicationId(dto.getApplicationId())
        .applicationNo(dto.getApplicationNo())
        .currentStatus(dto.getCurrentStatus())
        .build(); 
    }
}