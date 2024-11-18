package com.dsp.bulletin1.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;

import jakarta.servlet.http.HttpSession;

@Service
public interface Bulletin1DetailsService {

        Bulletin1DetailsDTO createBulletin1(Bulletin1DetailsDTO bulletin1DTO);

        Bulletin1DetailsDTO updateBulletin1(Long id, Bulletin1DetailsDTO bulletin1DTO);
    
        Bulletin1DetailsDTO getBulletin1ById(Long id);
    
        List<Bulletin1DetailsDTO> getAllBulletin1s();
        
        void deletebulletin1ById(Long id);

        Long getSessionId(HttpSession session);

        void setSessionId(Long id,HttpSession session);
        
        // List<Bulletin1DetailsDTO> getByLokSabhaSessionNo(Long lokSabhaNo);			

        List<Bulletin1DetailsDTO> getBulletin1s(Boolean isActive, Boolean saveAsDraft,Long lokSabhaNo,
        Long lokSabhaSessionNo, Long rajyaSabhaSessionNo,LocalDate startDate,LocalDate endDate);
}