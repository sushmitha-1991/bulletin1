package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.MasterCodeDTO;
import com.dsp.bulletin1.entity.MasterCode;

public  class CodeMapper {
    // Convert Entity to DTO
    public static MasterCodeDTO toDTO(MasterCode entity) {
    	MasterCodeDTO dto = new MasterCodeDTO();
            dto.setId(entity.getId());
            dto.setDescription(entity.getDescription());
            dto.setAbbr(entity.getAbbr());
            dto.setCode(entity.getCode());
            // dto.setCodeTypeId(entity.getCodeTypeId());
            // dto.setCreatedBy(entity.getCreatedBy());
            return dto;
        }
    
        public static MasterCode toEntity(MasterCodeDTO dto) {
            MasterCode entity = new MasterCode();
           
            entity.setId(dto.getId());
            entity.setDescription(dto.getDescription());
            entity.setAbbr(dto.getAbbr());
            entity.setCode(dto.getCode());
            // entity.setCodeTypeId(dto.getCodeTypeId());
            // entity.setCreatedBy(dto.getCreatedBy());           
             return entity;
    }
}