package com.dsp.bulletin1.bulletin2.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dsp.bulletin1.bulletin2.dto.Bulletin2DTO;

@Service
public interface Bulletin2Service {

    Bulletin2DTO createBulletin2(Bulletin2DTO lobDTO);
    Bulletin2DTO updateById(Long id, Bulletin2DTO lobDTO);
    Bulletin2DTO getById(Long id);
    void deleteById(Long id);
    List<Bulletin2DTO> getFilteredBulletins(Boolean isActive, Boolean saveAsDraft,Long lokSabhaNo,
    Long lokSabhaSessionNo, Long rajyaSabhaSessionNo,LocalDate startDate,LocalDate endDate);
}