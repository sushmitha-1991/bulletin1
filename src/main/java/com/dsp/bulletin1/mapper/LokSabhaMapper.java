package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.MastersLokSabhaDTO;
import com.dsp.bulletin1.entity.MastersLokSabha;

public  class LokSabhaMapper {
     public static MastersLokSabhaDTO toDTO(MastersLokSabha entity) {
    	MastersLokSabhaDTO dto = new MastersLokSabhaDTO();
            dto.setId(entity.getId());       
            // dto.setSpeaker(entity.getSpeaker());
            // dto.setDeputySpeaker(entity.getDeputySpeaker());     
            dto.setLokSabhaNumber(entity.getLokSabhaNumber());
            // dto.setStartDate(entity.getStartDate());
            // dto.setEndDate(entity.getEndDate());
            // dto.setTotalSessions(entity.getTotalSessions());
            // dto.setTotalSittings(entity.getTotalSittings());
            // dto.setCreatedBy(entity.getCreatedBy());
            return dto;
        }
    
        public static MastersLokSabha toEntity(MastersLokSabhaDTO dto) {
            MastersLokSabha entity = new MastersLokSabha();
           
            entity.setId(dto.getId());
            // entity.setSpeaker(dto.getSpeaker());
            // entity.setDeputySpeaker(dto.getDeputySpeaker());     
            entity.setLokSabhaNumber(dto.getLokSabhaNumber());
            // entity.setStartDate(dto.getStartDate());
            // entity.setEndDate(dto.getEndDate());
            // entity.setTotalSessions(dto.getTotalSessions());
            // entity.setTotalSittings(dto.getTotalSittings());
            // entity.setCreatedBy(dto.getCreatedBy());            
            return entity;
    }
}