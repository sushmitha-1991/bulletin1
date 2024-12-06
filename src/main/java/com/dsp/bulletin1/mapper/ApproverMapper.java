package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.ApplicationDTO;
import com.dsp.bulletin1.dto.ApproverDTO;
import com.dsp.bulletin1.entity.ApplicationEntity;
import com.dsp.bulletin1.entity.ApproverEntity;
import com.dsp.bulletin1.entity.BaseUser;
public class ApproverMapper {
        public static ApproverDTO toDTO(ApproverEntity entity) {
            ApproverDTO dto = new ApproverDTO();
               dto.setId(entity.getId());
               dto.setUsername(entity.getUsername());
               dto.setHouseId(entity.getHouseId());
                return dto;
            }
        
            public static ApproverEntity toEntity(ApproverDTO dto) {
                ApproverEntity entity = new ApproverEntity();
                entity.setId(dto.getId());
                entity.setUsername(dto.getUsername());   
                entity.setHouseId(dto.getHouseId());         
                return entity;
        }
    }
