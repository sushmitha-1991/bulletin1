
package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.ApplicationDTO;
import com.dsp.bulletin1.entity.ApplicationEntity;

public  class ApplicationMapper {
    // Convert Entity to DTO
    public static ApplicationDTO toDTO(ApplicationEntity entity) {
    	ApplicationDTO dto = new ApplicationDTO();
           dto.setId(entity.getId());
           dto.setName(entity.getName());
            return dto;
        }
    
        public static ApplicationEntity toEntity(ApplicationDTO dto) {
            ApplicationEntity entity = new ApplicationEntity();
            entity.setId(dto.getId());
            entity.setName(dto.getName());            
            return entity;
    }
}