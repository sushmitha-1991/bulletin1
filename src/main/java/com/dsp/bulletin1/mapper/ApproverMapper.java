package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.ApplicationDTO;
import com.dsp.bulletin1.dto.ApproverDTO;
import com.dsp.bulletin1.entity.ApplicationEntity;
import com.dsp.bulletin1.entity.BaseUser;
public class ApproverMapper {
        public static ApproverDTO toDTO(BaseUser entity) {
            ApproverDTO dto = new ApproverDTO();
               dto.setId(entity.getId());
               dto.setUsername(entity.getUsername());
                return dto;
            }
        
            public static BaseUser toEntity(ApproverDTO dto) {
                BaseUser entity = new BaseUser();
                entity.setId(dto.getId());
                entity.setUsername(dto.getUsername());            
                return entity;
        }
    }
