package com.dsp.bulletin1.bulletin2.service;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.dsp.bulletin1.bulletin2.dto.Bulletin2InputDTO;

@Service
public interface Bulletin2InputService {

    Bulletin2InputDTO createBulletin2(Bulletin2InputDTO lobDTO);

    Bulletin2InputDTO updateById(Long id, Bulletin2InputDTO lobDTO);

    Bulletin2InputDTO getById(Long id);

    void deleteById(Long id);

    List<Bulletin2InputDTO> getFilteredBulletins(Boolean isActive, Boolean saveAsDraft, Long lokSabhaNo,
    Long lokSabhaSessionNo, Long rajyaSabhaSessionNo,LocalDate startDate,LocalDate endDate);

}
