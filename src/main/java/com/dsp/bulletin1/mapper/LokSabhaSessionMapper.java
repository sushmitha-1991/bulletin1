package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.MastersLokSabhaSessionDTO;
import com.dsp.bulletin1.entity.MastersLokSabhaSession;

public  class LokSabhaSessionMapper {
    public static MastersLokSabhaSessionDTO   toDTO(MastersLokSabhaSession entity) {
    	MastersLokSabhaSessionDTO dto = new MastersLokSabhaSessionDTO();
            dto.setId(entity.getId());
          dto.setSessionNumber(entity.getSessionNumber());
            return dto;
        }
        public static MastersLokSabhaSession toEntity(MastersLokSabhaSessionDTO dto) {
            MastersLokSabhaSession entity = new MastersLokSabhaSession();
            entity.setId(dto.getId());
            entity.setSessionNumber(dto.getSessionNumber());
            return entity;
    }
}