package com.dsp.bulletin1.bulletin2.mapper;
import com.dsp.bulletin1.bulletin2.dto.Bulletin2DTO;
import com.dsp.bulletin1.bulletin2.entity.Bulletin2;
public class Bulletin2Mapper {

    // Convert Entity to DTO
    public static Bulletin2DTO toDTO(Bulletin2 entity) {
        return Bulletin2DTO.builder()
        .id(entity.getId())
        .lobDate(entity.getLobDate())
        .lobTime(entity.getLobTime())
        .lobType(entity.getLobType())
        .lokSabhaNo(entity.getLokSabhaNo())
        .note(entity.getNote())
        .status(entity.getStatus())
        .draftStatus(entity.getDraftStatus())
        .applicationNo(entity.getApplicationNo())
        .approverNameId(entity.getApproverNameId())
        .footNote(entity.getFootNote())
        .parentId(entity.getParentId())
        .place(entity.getPlace())
        .applicationId(entity.getApplicationId())
        .currentStatus(entity.getCurrentStatus())
        .build();
    }
    // Convert DTO to Entity
    public static Bulletin2 toEntity(Bulletin2DTO dto) {
        return Bulletin2.builder()
        .id(dto.getId())
        .lobDate(dto.getLobDate())
        .lobTime(dto.getLobTime())
        .lobType(dto.getLobType())
        .lokSabhaNo(dto.getLokSabhaNo())
        .note(dto.getNote())
        .status(dto.getStatus())
        .draftStatus(dto.getDraftStatus())
        .applicationNo(dto.getApplicationNo())
        .approverNameId(dto.getApproverNameId())
        .footNote(dto.getFootNote())
        .parentId(dto.getParentId())
        .place(dto.getPlace())
        .applicationId(dto.getApplicationId())
        .currentStatus(dto.getCurrentStatus())
        .build();
    }
}
     