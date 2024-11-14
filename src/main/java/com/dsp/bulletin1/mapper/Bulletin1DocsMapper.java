package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.Bulletin1DocsDTO;
import com.dsp.bulletin1.entity.Bulletin1Docs;

public class Bulletin1DocsMapper {

    // Convert Entity to DTO
    public static Bulletin1DocsDTO toDTO(Bulletin1Docs entity) {
        Bulletin1DocsDTO dto = new Bulletin1DocsDTO();
        dto.setId(entity.getId());
        dto.setBulletin1Id(entity.getBulletin1Id());
        dto.setRefDocDate(entity.getRefDocDate());
        dto.setRefDocId(entity.getRefDocId());
        return dto;
    }

    // Convert DTO to Entity
    public static Bulletin1Docs toEntity(Bulletin1DocsDTO dto) {
    	Bulletin1Docs entity = new Bulletin1Docs();
        entity.setId(dto.getId());
        entity.setBulletin1Id(dto.getBulletin1Id());
        entity.setRefDocDate(dto.getRefDocDate());
        entity.setRefDocId(dto.getRefDocId());    
        return entity;
    }
}
 