package com.dsp.bulletin1.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsp.bulletin1.dto.ApproverDTO;
import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaSessionDTO;
import com.dsp.bulletin1.dto.MastersRajyaSabhaSessionDTO;
import com.dsp.bulletin1.entity.Bulletin1Details;
import com.dsp.bulletin1.exception.UserNotFoundException;
import com.dsp.bulletin1.mapper.ApproverMapper;
import com.dsp.bulletin1.mapper.Bulletin1DetailsMapper;
import com.dsp.bulletin1.mapper.LokSabhaMapper;
import com.dsp.bulletin1.mapper.LokSabhaSessionMapper;
import com.dsp.bulletin1.mapper.RajyaSabhaSessionMapper;
import com.dsp.bulletin1.repository.Bulletin1DetailsRepository;
import com.dsp.bulletin1.repository.MasterLSRepository;
import com.dsp.bulletin1.repository.MasterLSSRepository;
import com.dsp.bulletin1.repository.MastersRSSRepository;
import com.dsp.bulletin1.repository.MyUserRepository;
import com.dsp.bulletin1.service.Bulletin1DetailsService;

import jakarta.servlet.http.HttpSession;
@Service
public class Bulletin1DetailsServiceImpl implements Bulletin1DetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(Bulletin1DetailsServiceImpl.class);
	private static final String USER_ID_SESSION_ATTR = "userId";
	private final Bulletin1DetailsRepository bulletin1Repository;
    private final MasterLSRepository masterLSRepository;
    private final MasterLSSRepository masterLSSRepository;
    private final MastersRSSRepository mastersRSSRepository;
    private final MyUserRepository userRepository;
	@Autowired
	public Bulletin1DetailsServiceImpl(Bulletin1DetailsRepository bulletin1Repository,
    MasterLSRepository masterLSRepository, MyUserRepository userRepository,
    MasterLSSRepository masterLSSRepository, MastersRSSRepository mastersRSSRepository)
    {	
        this.userRepository=userRepository;
        this.mastersRSSRepository=mastersRSSRepository;
        this.bulletin1Repository = bulletin1Repository;
        this.masterLSRepository=masterLSRepository;
        this.masterLSSRepository=masterLSSRepository;
	}

	@Override
	public Bulletin1DetailsDTO createBulletin1(Bulletin1DetailsDTO bulletin1DTO) {
        
		Bulletin1Details bulletin1Details = Bulletin1DetailsMapper.toEntity(bulletin1DTO);
        
		bulletin1Details = bulletin1Repository.save(bulletin1Details);
		return Bulletin1DetailsMapper.toDTO(bulletin1Details);
	}
	@Override
	public Bulletin1DetailsDTO updateBulletin1(Long id, Bulletin1DetailsDTO bulletin1DTO) {
		Bulletin1Details existingBulletin1 = bulletin1Repository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Bulletin1 Details not found with ID: " + id));;
		Bulletin1Details updatedBulletin1= Bulletin1DetailsMapper.toEntity(bulletin1DTO);
		updatedBulletin1.setId(existingBulletin1.getId());
		bulletin1Repository.save(updatedBulletin1);
		return Bulletin1DetailsMapper.toDTO(updatedBulletin1);
	}
	@Override
	public Bulletin1DetailsDTO getBulletin1ById(Long id) {
        Bulletin1Details entity = bulletin1Repository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Bulletin1 Details  not found with ID: " + id));
        Bulletin1DetailsDTO dto = Bulletin1DetailsMapper.toDTO(entity); 
        if(entity.getLokSabhaSessionNo()!=null)
        {
            MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
            dto.setLokSabhaSessionDetails(lokSabhaSession);
        }
        if(entity.getLokSabhaNo()!=null)
        {
            MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
            dto.setLokSabhaDetails(lokSabha);
        }
        if(entity.getRajyaSabhaSessionNo()!=null)
        {
            MastersRajyaSabhaSessionDTO rajyaSabhaSession=getRajyaSabhaSessionDetails(entity.getRajyaSabhaSessionNo());
            dto.setRajyaSabhaSessionDetails(rajyaSabhaSession);
        }
        //mastersRSSRepository
        if(entity.getApproverId()!=null)
        {
            ApproverDTO approver =getApproverDetails(entity.getApproverId());
            dto.setApproverDetails(approver); 
        }
                      
        return dto;
    }
    private ApproverDTO getApproverDetails(Long id) {
        return userRepository.findById(id)
            .map(ApproverMapper::toDTO)  // Convert to DTO
            .orElse(null);
    }
    @Override
	public List<Bulletin1DetailsDTO> getAllBulletin1s() 
    {   
        List<Bulletin1DetailsDTO> dtoList = new ArrayList<>();
        for (Bulletin1Details entity : bulletin1Repository.findAll()) 
        {
            Bulletin1DetailsDTO dto = Bulletin1DetailsMapper.toDTO(entity); 

            if(entity.getLokSabhaSessionNo()!=null)
            {
                MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
                dto.setLokSabhaSessionDetails(lokSabhaSession);
            }
            if(entity.getLokSabhaNo()!=null)
            {
                MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
                dto.setLokSabhaDetails(lokSabha);
            }
            if(entity.getRajyaSabhaSessionNo()!=null)
            {
                MastersRajyaSabhaSessionDTO rajyaSabhaSession=getRajyaSabhaSessionDetails(entity.getRajyaSabhaSessionNo());
                dto.setRajyaSabhaSessionDetails(rajyaSabhaSession);
            }
            if(entity.getApproverId()!=null)
            {
                ApproverDTO approver=getApproverDetails(entity.getApproverId());
                dto.setApproverDetails(approver); 
            }  
            dtoList.add(dto);    
        }
        return dtoList;
	}
    private MastersLokSabhaSessionDTO getLokSabhaSessionDetails(Long id) {
        return masterLSSRepository.findById(id)
            .map(LokSabhaSessionMapper::toDTO)  // Convert to DTO
            .orElse(null);  // Return null if not found
    }
    private MastersRajyaSabhaSessionDTO getRajyaSabhaSessionDetails(Long id) {
        return mastersRSSRepository.findById(id)
            .map(RajyaSabhaSessionMapper::toDTO)  // Convert to DTO
            .orElse(null); 
    }
    private MastersLokSabhaDTO getLokSabhaDetails(Long id) {
        return masterLSRepository.findById(id)
            .map(LokSabhaMapper::toDTO)  // Convert to DTO
            .orElse(null);  // Return null if not found
    }
	@Override
	public void deletebulletin1ById(Long id) {
		if (!bulletin1Repository.existsById(id)) {
			throw new UserNotFoundException("Bulletin1 Details not found with ID: " + id);
		}
		bulletin1Repository.deleteById(id);
	}
	@Override
    public Long getSessionId(HttpSession session) {
		Long userId = (Long) session.getAttribute(USER_ID_SESSION_ATTR);
        if (userId != null) {
            LOGGER.info("Retrieved User ID {} from session.", userId);
        } else {
            LOGGER.warn("No User ID found in session.");
        }
        return userId;
    }
	@Override
	public void setSessionId(Long userId,HttpSession session) {
        if (userId != null) {
            session.setAttribute(USER_ID_SESSION_ATTR, userId);
            LOGGER.info("User ID {} set in session.", userId);
        } else {
            LOGGER.warn("Attempted to set a null user ID in session.");
        }
    }
    private void validateRequiredFields(Bulletin1DetailsDTO bulletin1DTO) {
        if (bulletin1DTO.getLokSabhaNo() == null) {
            throw new IllegalArgumentException("Lok Sabha No is required for finalization.");
        }
        if (bulletin1DTO.getLokSabhaSessionNo() == null) {
            throw new IllegalArgumentException("Session No is required for finalization.");
        }
    }
       
    public List<Bulletin1DetailsDTO> getByLokSabhaSessionNo(Long lokSabhaSessionNo) {
        List<Bulletin1DetailsDTO> dtoList = new ArrayList<>();
        for (Bulletin1Details entity : bulletin1Repository.findByLokSabhaSessionNo(lokSabhaSessionNo)) 
        {
            Bulletin1DetailsDTO dto = Bulletin1DetailsMapper.toDTO(entity);    
            if(entity.getLokSabhaSessionNo()!=null)
            {
                MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
                dto.setLokSabhaSessionDetails(lokSabhaSession);
            }
            if(entity.getLokSabhaNo()!=null)
            {
                MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
                dto.setLokSabhaDetails(lokSabha);
            }
            if(entity.getRajyaSabhaSessionNo()!=null)
            {
                MastersRajyaSabhaSessionDTO rajyaSabhaSession=getRajyaSabhaSessionDetails(entity.getRajyaSabhaSessionNo());
                dto.setRajyaSabhaSessionDetails(rajyaSabhaSession);
            }
            if(entity.getApproverId()!=null)
            {
                ApproverDTO approver=getApproverDetails(entity.getApproverId());
                dto.setApproverDetails(approver); 
            
            }
            dtoList.add(dto);
    
            }
            return dtoList;
    }
}


