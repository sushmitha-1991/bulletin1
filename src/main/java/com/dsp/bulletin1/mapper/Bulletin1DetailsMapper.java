package com.dsp.bulletin1.mapper;

import org.mapstruct.Mapper;

import com.dsp.bulletin1.dto.ApplicationInstanceDTO;
import com.dsp.bulletin1.dto.ApproverDTO;
import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaSessionDTO;
import com.dsp.bulletin1.dto.MastersRajyaSabhaSessionDTO;
import com.dsp.bulletin1.entity.Bulletin1Details;

@Mapper(componentModel = "string")
public interface Bulletin1DetailsMapper {
    // Convert Entity to DTO
    public static Bulletin1DetailsDTO toDTO(Bulletin1Details entity) {
        Bulletin1DetailsDTO dto = Bulletin1DetailsDTO.builder()
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
                .footNotes(entity.getFootNotes())
                .documents(entity.getDocuments())
                .lokSabhaNo(entity.getLokSabhaNo())
                .lokSabhaSessionNo(entity.getLokSabhaSessionNo())
                .rajyaSabhaSessionNo(entity.getRajyaSabhaSessionNo())
                .applicationId(entity.getApplicationId())
                .applicationNo(entity.getApplicationNo())
                .currentStatus(entity.getCurrentStatus())
                .refId(entity.getRefId())
                .applicationInstanceDTO(ApplicationInstanceDTO.builder()
                        .id(entity.getId())
                        .applicationId(entity.getApplicationId())
                        .applicationNo(entity.getApplicationNo())
                        .currentStatus(entity.getCurrentStatus())
                        .refId(entity.getRefId())
                        .documentDate(entity.getDocumentDate())
                        .build())
                .build();

        if (entity.getLokSabhaSessionDetails() != null) {
            dto.setLokSabhaSessionDTO(MastersLokSabhaSessionDTO.builder()
                    .id(entity.getLokSabhaSessionDetails().getId())
                    .sessionNumber(entity.getLokSabhaSessionDetails().getSessionNumber())
                    .build());
            dto.setLokSabhaDTO(MastersLokSabhaDTO.builder()
                    .id(entity.getLokSabhaSessionDetails().getLokSabha().getId())
                    .lokSabhaNumber(entity.getLokSabhaSessionDetails().getLokSabha().getLokSabhaNumber())
                    .build());
        }
        if (entity.getRajyaSabhaSessionDetails() != null) {
            dto.setRajyaSabhaSessionDTO(MastersRajyaSabhaSessionDTO.builder()
                    .id(entity.getRajyaSabhaSessionDetails().getId())
                    .sessionNumber(entity.getRajyaSabhaSessionDetails().getSessionNumber())
                    .build());
        }
        if (entity.getApproverDetails() != null) {
            dto.setApproverDTO(ApproverDTO.builder()
                    .id(entity.getApproverDetails().getId())
                    .houseId(entity.getApproverDetails().getHouseId())
                    .username(entity.getApproverDetails().getUsername())
                    .build());
        }

        return dto;
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
                // .documentDate(dto.getApplicationInstanceDTO().getDocumentDate())
                .refId(dto.getRefId())
                .build();
    }
}