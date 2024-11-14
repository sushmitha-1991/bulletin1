package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.MastersBranchDTO;
import com.dsp.bulletin1.entity.MastersBranch;

public  class BranchMapper {
    // Convert Entity to DTO
    public static MastersBranchDTO toDTO(MastersBranch entity) {
    	MastersBranchDTO dto = new MastersBranchDTO();
            dto.setId(entity.getId());
            // dto.setDescription(entity.getDescription());
            // dto.setHouseId(entity.getHouseId());
            // dto.setOfficeCode(entity.getOfficeCode());
            dto.setOfficeName(entity.getOfficeName());
            // dto.setCreatedBy(entity.getCreatedBy());
            return dto;
        }
    
        public static MastersBranch toEntity(MastersBranchDTO dto) {
            MastersBranch entity = new MastersBranch();
            entity.setId(dto.getId());
            // entity.setDescription(dto.getDescription());
            // entity.setHouseId(dto.getHouseId());
            // entity.setOfficeCode(dto.getOfficeCode());
            entity.setOfficeName(dto.getOfficeName());
            // entity.setCreatedBy(dto.getCreatedBy());            
            return entity;
    }
}