package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.MastersRajyaSabhaSessionDTO;
import com.dsp.bulletin1.entity.MastersRajyaSabhaSession;

public  class RajyaSabhaSessionMapper {
    public static MastersRajyaSabhaSessionDTO   toDTO(MastersRajyaSabhaSession entity) {
    	MastersRajyaSabhaSessionDTO dto = new MastersRajyaSabhaSessionDTO();
            dto.setId(entity.getId());
            dto.setSessionNumber(entity.getSessionNumber());
            return dto;
        }
        public static MastersRajyaSabhaSession toEntity(MastersRajyaSabhaSessionDTO dto) {
            MastersRajyaSabhaSession entity = new MastersRajyaSabhaSession();
            entity.setId(dto.getId());
            entity.setSessionNumber(dto.getSessionNumber());
            return entity;
    }
}