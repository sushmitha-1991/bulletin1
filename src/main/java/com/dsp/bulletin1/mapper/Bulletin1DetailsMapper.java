package com.dsp.bulletin1.mapper;
import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.entity.Bulletin1Details;
public  class Bulletin1DetailsMapper {
    // Convert Entity to DTO
    public static Bulletin1DetailsDTO toDTO(Bulletin1Details entity) {
    	Bulletin1DetailsDTO dto = new Bulletin1DetailsDTO();
            dto.setId(entity.getId());
            dto.setBulletinDate(entity.getBulletinDate());
            dto.setBulletinDateHindi(entity.getBulletinDateHindi());
            dto.setDraftStatus(entity.getDraftStatus());
            dto.setApproverId(entity.getApproverId());   
            dto.setLokSabhaNo(entity.getLokSabhaNo());
            dto.setLokSabhaSessionNo(entity.getLokSabhaSessionNo());
            dto.setRajyaSabhaSessionNo(entity.getRajyaSabhaSessionNo());  
            return dto;
        }
    
        public static Bulletin1Details toEntity(Bulletin1DetailsDTO dto) {
            Bulletin1Details entity = new Bulletin1Details();
            entity.setId(dto.getId());
            entity.setBulletinDate(dto.getBulletinDate());
            entity.setBulletinDateHindi(dto.getBulletinDateHindi());
            entity.setDraftStatus(dto.getDraftStatus());
            entity.setApproverId(dto.getApproverId());   
            entity.setLokSabhaNo(dto.getLokSabhaNo());
            entity.setLokSabhaSessionNo(dto.getLokSabhaSessionNo());
            entity.setRajyaSabhaSessionNo(dto.getRajyaSabhaSessionNo());  
            return entity;
    }
}