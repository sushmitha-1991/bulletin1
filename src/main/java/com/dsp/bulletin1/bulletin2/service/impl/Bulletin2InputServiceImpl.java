package com.dsp.bulletin1.bulletin2.service.impl;

    import java.time.LocalDate;
import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;
    
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.jpa.domain.Specification;
    import org.springframework.stereotype.Service;

import com.dsp.bulletin1.bulletin2.dto.Bulletin2InputDTO;
import com.dsp.bulletin1.bulletin2.entity.Bulletin2Input;
import com.dsp.bulletin1.bulletin2.entity.entitySpecification.Bulletin2InputSpec;
import com.dsp.bulletin1.bulletin2.mapper.Bulletin2InputMapper;
import com.dsp.bulletin1.bulletin2.repository.Bulletin2InputRepository;
import com.dsp.bulletin1.bulletin2.service.Bulletin2InputService;
import com.dsp.bulletin1.bulletin2.service.Bulletin2Service;
import com.dsp.bulletin1.dto.MasterCodeDTO;
import com.dsp.bulletin1.dto.MastersBranchDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaSessionDTO;
import com.dsp.bulletin1.entity.enums.Status;
import com.dsp.bulletin1.exception.UserNotFoundException;
import com.dsp.bulletin1.mapper.BranchMapper;
import com.dsp.bulletin1.mapper.CodeMapper;
import com.dsp.bulletin1.mapper.LokSabhaMapper;
import com.dsp.bulletin1.mapper.LokSabhaSessionMapper;
import com.dsp.bulletin1.repository.MasterBranchRepository;
import com.dsp.bulletin1.repository.MasterCodeRepository;
import com.dsp.bulletin1.repository.MasterLSRepository;
import com.dsp.bulletin1.repository.MasterLSSRepository;

import jakarta.servlet.http.HttpSession;
    @Service
    public class Bulletin2InputServiceImpl implements Bulletin2InputService {
    
        private static final Logger INPUTLOGGER = LogManager.getLogger(Bulletin2InputServiceImpl.class);
        private static final String USER_ID_SESSION_ATTR = "userInputId";
        private final Bulletin2InputRepository bulletin2Repository;
        private final MasterCodeRepository masterRepository;
        private final MasterLSRepository masterLSRepository;
        private final MasterBranchRepository masterBranchRepository;
        private final MasterLSSRepository masterLSSRepository;
        private final Bulletin2InputMapper mapper;
        @Autowired
        public Bulletin2InputServiceImpl(Bulletin2InputRepository bulletin2Repository,
        MasterCodeRepository masterRepository, MasterLSRepository masterLSRepository,
        MasterBranchRepository masterBranchRepository,MasterLSSRepository masterLSSRepository,
        Bulletin2InputMapper mapper)
        {	
            this.bulletin2Repository = bulletin2Repository;
            this.masterRepository=masterRepository;
            this.masterBranchRepository=masterBranchRepository;
            this.masterLSRepository=masterLSRepository;
            this.masterLSSRepository=masterLSSRepository;
            this.mapper=mapper;
        }
    
        @Override
        public Bulletin2InputDTO createBulletin2(Bulletin2InputDTO bulletin2DTO) 
        {
            if(bulletin2DTO.getDraftFlag())
            {
                if(bulletin2DTO.getLokSabhaNo()==null && bulletin2DTO.getLokSabhaSessionNo()==null)
                {
                    String refNo= "DB2RS"+bulletin2DTO.getRajyaSabhaSessionNo()+bulletin2DTO.getId();
                    bulletin2DTO.setRefNo(refNo);
                }
                else{
                    String refNo= "DB2LS"+bulletin2DTO.getLokSabhaNo()+bulletin2DTO.getLokSabhaSessionNo()+bulletin2DTO.getId();
                    bulletin2DTO.setRefNo(refNo);
                }
                bulletin2DTO.setStatus(Status.DRAFTED);
            }
            else
            {
                if(bulletin2DTO.getLokSabhaNo()==null && bulletin2DTO.getLokSabhaSessionNo()==null)
                {
                    String refNo= "B2RS"+bulletin2DTO.getRajyaSabhaSessionNo()+bulletin2DTO.getId();
                    bulletin2DTO.setRefNo(refNo);
                }
                else{
                    String refNo= "B2LS"+bulletin2DTO.getLokSabhaNo()+bulletin2DTO.getLokSabhaSessionNo()+bulletin2DTO.getId();
                    bulletin2DTO.setRefNo(refNo);
                }
                bulletin2DTO.setDraftFlag(false);
            }
            Bulletin2Input bulletin2Input =
            mapper.toEntity(bulletin2DTO);
            INPUTLOGGER.debug("Entering save Bulletin2 : {}", bulletin2Input);
             
            try { 
                INPUTLOGGER.debug("Converted Bulletin2 DTO to entity: {}", bulletin2Input);
                Bulletin2Input savedBulletin2 = bulletin2Repository.save(bulletin2Input);
                INPUTLOGGER.debug("Saved Bulletin2 Input entity : {}", savedBulletin2);
                    if(savedBulletin2.getLokSabhaSessionNo()==null && savedBulletin2.getLokSabhaNo()==null)
                    {
                        INPUTLOGGER.info(
                    "Saved Bulletin2 generate attributes: ID={}, RajyaSabhaSessionNo={},  Status={}, RefNo={}",
                    savedBulletin2.getId(),  savedBulletin2.getRajyaSabhaSessionNo(), savedBulletin2.getStatus(),
                    savedBulletin2.getRefNo());
                    }
                    else{
                       INPUTLOGGER.info(
                            "Saved Bulletin2 generate attributes: ID={}, LokSabhaNo={}, SessionNo={},  Status={}, RefNO={}",
                            savedBulletin2.getId(), savedBulletin2.getLokSabhaNo(), savedBulletin2.getLokSabhaSessionNo(), 
                            savedBulletin2.getStatus(),savedBulletin2.getRefNo());
                    }
                    // getCodeDetails
                    Bulletin2InputDTO responseDTO = mapper.toDTO(savedBulletin2);
                    return responseDTO;
    
                }
            catch (Exception e) {
                INPUTLOGGER.error("Error in save Bulletin2: ", e);
                throw e;
            }
        }
        @Override
        public Bulletin2InputDTO updateById(Long id, Bulletin2InputDTO bulletin2DTO) {
            if (!bulletin2Repository.existsById(id)) {
                INPUTLOGGER.error("Bulletin2 Input not found with ID: " ,id);
                throw new UserNotFoundException("Bulletin2 Input not found with ID: " + id);
            }
            else{
                INPUTLOGGER.info("Bulletin2 Input found with ID: " ,id);
            }
            Bulletin2Input existingInput = bulletin2Repository.findById(id)
            .orElseThrow(() -> {
                INPUTLOGGER.error("Bulletin2 not found with ID : {}", id);
                return new UserNotFoundException("Bulletin2 not found with ID: " + id);
            });
            Bulletin2Input updatedInput = mapper.toEntity(bulletin2DTO);
            updatedInput.setId(existingInput.getId());
            bulletin2Repository.save(updatedInput);
            return mapper.toDTO(updatedInput);
        }
        @Override
        public Bulletin2InputDTO getById(Long id) {
            if (!bulletin2Repository.existsById(id)) {
                INPUTLOGGER.error("Bulletin2 Input not found with ID: " ,id);
                throw new UserNotFoundException("Bulletin2 Input not found with ID: " + id);
            }
            else{
                INPUTLOGGER.info("Bulletin2 Input found with ID: " ,id);
            }
            Bulletin2Input entity = bulletin2Repository.findById(id)
            .orElseThrow(() -> {
                INPUTLOGGER.error("Bulletin2 not found with ID: {}", id);
                return new UserNotFoundException("BUlletin2 not found with ID: " + id);
            });
            
            Bulletin2InputDTO dto = mapper.toDTO(entity); 
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
            if(entity.getOfficeId()!=null)
            {
                MastersBranchDTO branch=getBranchDetails(entity.getOfficeId());
                dto.setOfficeDetails(branch); 
            }
            if(entity.getSubject()!=null)
            {
                MasterCodeDTO code=getCodeDetails(entity.getSubject());
                dto.setSubjectDetails(code); 
            }               
            return dto;
        }
        private MastersBranchDTO getBranchDetails(Long id) {
            return masterBranchRepository.findById(id)
                .map(BranchMapper::toDTO)  // Convert to DTO
                .orElse(null);  // Return null if not found
        }
      
        private MasterCodeDTO getCodeDetails(Long id) {
            return masterRepository.findById(id)
                .map(CodeMapper::toDTO)  // Convert to DTO
                .orElse(null);  // Return null if not found
        }
        private MastersLokSabhaSessionDTO getLokSabhaSessionDetails(Long id) {
            // return masterLSSRepository.findById(id)
            return masterLSSRepository.findById(id)
                .map(LokSabhaSessionMapper::toDTO)  // Convert to DTO
                .orElse(null);  // Return null if not found
        }
        
        private MastersLokSabhaDTO getLokSabhaDetails(Long id) {
            return masterLSRepository.findById(id)
                .map(LokSabhaMapper::toDTO)  // Convert to DTO
                .orElse(null);  // Return null if not found
        }
        @Override
        public void deleteById(Long id) {
            INPUTLOGGER.error("Attemting deleting Bulletin2 Input with ID: " ,id);
            if (!bulletin2Repository.existsById(id)) {
                INPUTLOGGER.error("Bulletin2 Input not found with ID: " ,id);
                throw new UserNotFoundException("Bulletin2 Input not found with ID: " + id);
            }
            else{
                INPUTLOGGER.info("Bulletin2 Input found with ID: " ,id);
            }
            Bulletin2Input existingInput = bulletin2Repository.findById(id)
            .orElseThrow(() -> {
                INPUTLOGGER.error("Bulletin2 not found with ID : {}", id);
                return new UserNotFoundException("Bulletin2 not found with ID: " + id);
            });
            Bulletin2InputDTO bulletin2DTO=mapper.toDTO(existingInput);
            bulletin2DTO.setStatus(Status.INACTIVE);
            Bulletin2Input updatedInput = mapper.toEntity(bulletin2DTO);
            updatedInput.setId(existingInput.getId());
            bulletin2Repository.save(updatedInput);
            INPUTLOGGER.error("Bulletin2Input is INACTIVATED or DELETED with ID : {}", id);
            
        }

        @Override
        public List<Bulletin2InputDTO> getFilteredBulletins(Boolean isActive, Boolean saveAsDraft,Long lokSabhaNo,
         Long lokSabhaSessionNo, Long rajyaSabhaSessionNo,LocalDate startDate,LocalDate endDate)  {
        List<Bulletin2InputDTO> dtoList = new ArrayList<>();
        List<Bulletin2Input> entitys;
        if(saveAsDraft==null)
        {
            saveAsDraft=false;
        }
        if(isActive==null)
        {
            isActive=true;
        }
        // if(rajyaSabhaSessionNo==null)
        // {
            // rajyaSabhaSessionNo=getCurrentRajyaSabhaSession();
        // }
        // if(lokSabhaSessionNo==null)
        // {
        //     lokSabhaSessionNo=getCurrentLokSabhaSession();
        // }
        if(startDate!=null || endDate!=null) 
        {
            entitys = bulletin2Repository.findAll(Specification.where(Bulletin2InputSpec.isActive(isActive))
        .and(Bulletin2InputSpec.hasSaveAsDraft(saveAsDraft))
        .and(Bulletin2InputSpec.hasDateBetween(startDate, endDate))
        .and(Bulletin2InputSpec.hasLokSabhaSessionNo(lokSabhaSessionNo))).stream()
        .collect(Collectors.toList());
        }
        else if(lokSabhaSessionNo!=null) 
        {     
        entitys = bulletin2Repository.findAll(Specification.where(Bulletin2InputSpec.isActive(isActive))
        .and(Bulletin2InputSpec.hasSaveAsDraft(saveAsDraft))
        .and(Bulletin2InputSpec.hasLokSabhaSessionNo(lokSabhaSessionNo))).stream()
        .collect(Collectors.toList());
        }
        else if(rajyaSabhaSessionNo!=null){
        entitys= bulletin2Repository.findAll(Specification.where(Bulletin2InputSpec.isActive(isActive))
        .and(Bulletin2InputSpec.hasSaveAsDraft(saveAsDraft))
        .and(Bulletin2InputSpec.hasRajyaSabhaSessionNo(rajyaSabhaSessionNo))).stream()
        .collect(Collectors.toList());
        }
        else
        {
        entitys= bulletin2Repository.findAll(Specification.where(Bulletin2InputSpec.isActive(isActive))
        .and(Bulletin2InputSpec.hasSaveAsDraft(saveAsDraft)))
        .stream().collect(Collectors.toList());
        }
        for (Bulletin2Input entity : entitys) 
        {
        Bulletin2InputDTO dto = mapper.toDTO(entity);         
        
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
        if(entity.getOfficeId()!=null)
        {
            MastersBranchDTO branch=getBranchDetails(entity.getOfficeId());
            dto.setOfficeDetails(branch); 
        }
        if(entity.getSubject()!=null)
        {
            MasterCodeDTO code=getCodeDetails(entity.getSubject());
            dto.setSubjectDetails(code); 
        }   
        dtoList.add(dto);
        }
        return dtoList;
    }
    // private Long getCurrentLokSabhaSession() {
    // // TODO Auto-generated method stub
    // }
    }
