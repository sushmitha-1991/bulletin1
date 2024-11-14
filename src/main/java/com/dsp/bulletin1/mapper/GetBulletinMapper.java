package com.dsp.bulletin1.mapper;

import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.dto.BulletinDTO;
import com.dsp.bulletin1.entity.Bulletin1Details;

public class GetBulletinMapper {
 
    public static BulletinDTO toDTO(Bulletin1Details entity) 
    {
    	BulletinDTO dto = new BulletinDTO();
            dto.setId(entity.getId());
            dto.setBulletinNo(entity.getBulletinNo());
            dto.setFootNotes(entity.getFootNotes());
            return dto;
    }
    public static Bulletin1Details toEntity(BulletinDTO dto) 
    {
            Bulletin1Details entity = new Bulletin1Details();
            entity.setId(dto.getId());
            entity.setBulletinNo(dto.getBulletinNo());
            entity.setFootNotes(dto.getFootNotes());
            return entity;
    }
}
