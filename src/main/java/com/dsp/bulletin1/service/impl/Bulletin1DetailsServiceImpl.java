package com.dsp.bulletin1.service.impl;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dsp.bulletin1.dto.ApplicationInstanceDTO;
import com.dsp.bulletin1.dto.ApproverDTO;
import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.dto.SerialNumberRequest;
import com.dsp.bulletin1.dto.SerialNumberResponse;
import com.dsp.bulletin1.dto.UserDetailsDTO;
import com.dsp.bulletin1.entity.Bulletin1Details;
import com.dsp.bulletin1.entity.Bulletin1Docs;
import com.dsp.bulletin1.entity.MastersLokSabhaSession;
import com.dsp.bulletin1.entity.MastersRajyaSabhaSession;
import com.dsp.bulletin1.entity.enums.Status;
import com.dsp.bulletin1.entity.specification.Bulletin1Spec;
import com.dsp.bulletin1.exception.UserNotFoundException;
import com.dsp.bulletin1.mapper.ApproverMapper;
import com.dsp.bulletin1.mapper.Bulletin1DetailsMapper;
import com.dsp.bulletin1.repository.Bulletin1DetailsRepository;
import com.dsp.bulletin1.repository.Bulletin1DocsRepository;
import com.dsp.bulletin1.repository.MasterLSRepository;
import com.dsp.bulletin1.repository.MasterLSSRepository;
import com.dsp.bulletin1.repository.MastersRSSRepository;
import com.dsp.bulletin1.repository.MyUserRepository;
import com.dsp.bulletin1.service.Bulletin1DetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class Bulletin1DetailsServiceImpl implements Bulletin1DetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bulletin1DetailsServiceImpl.class);
    private static final String USER_ID_SESSION_ATTR = "userId";
    @Value("${master.service.url}")
    private String masterServiceUrl;
    @Autowired
    private HttpServletRequest httpRequest;
    @Autowired
    private RestTemplate restTemplate;
    private final Bulletin1DetailsRepository bulletin1Repository;
    private final MasterLSRepository masterLSRepository;
    private final MasterLSSRepository masterLSSRepository;
    private final MastersRSSRepository mastersRSSRepository;
    private final MyUserRepository userRepository;
    private final Bulletin1DocsRepository bulletinDocsRepo;

    @Autowired
    public Bulletin1DetailsServiceImpl(Bulletin1DetailsRepository bulletin1Repository,
            MasterLSRepository masterLSRepository, MyUserRepository userRepository,
            MasterLSSRepository masterLSSRepository, MastersRSSRepository mastersRSSRepository,
            Bulletin1DocsRepository bulletinDocsRepo) {
        this.userRepository = userRepository;
        this.mastersRSSRepository = mastersRSSRepository;
        this.bulletin1Repository = bulletin1Repository;
        this.masterLSRepository = masterLSRepository;
        this.masterLSSRepository = masterLSSRepository;
        this.bulletinDocsRepo = bulletinDocsRepo;
    }

    @Override
    public Bulletin1DetailsDTO createBulletin1(Bulletin1DetailsDTO bulletin1DTO) {
        if (bulletin1DTO.getDraftStatus() == null) {
            bulletin1DTO.setDraftStatus(false);
        }
        if (bulletin1DTO.getDraftStatus()) {
            bulletin1DTO.setStatus(Status.DRAFTED);
        }
        if (bulletin1DTO.getStatus() == null) {
            bulletin1DTO.setStatus(Status.IN_PROGRESS);
        }
        bulletin1DTO.setCurrentStatus("" + bulletin1DTO.getStatus());
        // Save By House
        Long houseId = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsDTO) {
            houseId = ((UserDetailsDTO) principal).getHouseId();
        }
        Bulletin1Details bulletin1Details = Bulletin1DetailsMapper.toEntity(bulletin1DTO);
        SerialNumberRequest serialNumberRequest = new SerialNumberRequest();
        serialNumberRequest.setAppId(bulletin1Details.getApplicationId());
        if (houseId == 1) {
            if (bulletin1Details.getRajyaSabhaSessionNo() == null) {

                Long rajyaSabhaSession = mastersRSSRepository.findLatestByCreatedBy();
                MastersRajyaSabhaSession session = mastersRSSRepository.findById(rajyaSabhaSession).get();
                bulletin1Details.setRajyaSabhaSessionNo(rajyaSabhaSession);
                bulletin1Details.setRajyaSabhaSession(session.getSessionNumber());
            }
            bulletin1Details.setLokSabhaNo(null);
            bulletin1Details.setLokSabhaSessionNo(null);
            serialNumberRequest.setSessionId(bulletin1Details.getRajyaSabhaSessionNo());

        } else if (houseId == 2) {
            if (bulletin1Details.getLokSabhaSessionNo() == null) {
                Long lokSabhaSession = masterLSSRepository.findLatestByCreatedBy();
                MastersLokSabhaSession session = masterLSSRepository.findById(lokSabhaSession).get();
                bulletin1Details.setLokSabhaSessionNo(lokSabhaSession);
                bulletin1Details.setLokSabhaSessionNumber(session.getSessionNumber());
                bulletin1Details.setLokSabhaNo(session.getLokSabha().getId());
                bulletin1Details.setLokSabhaSession(session.getLokSabha().getLokSabhaNumber());
            }
            bulletin1Details.setRajyaSabhaSessionNo(null);
            serialNumberRequest.setSessionId(bulletin1Details.getLokSabhaSessionNo());
        }
        SerialNumberResponse application = generateApplicationNo(serialNumberRequest);
        bulletin1Details.setApplicationNo(application.getId());
        bulletin1Details.setRefNo(bulletin1Details.getApplicationNo());
        LOGGER.debug("Entering save Bulletin1  with Bulletin1 DTO: {}", bulletin1Details);
        try {
            LOGGER.debug("Converted Bulletin1 DTO to entity: {}", bulletin1Details);
            bulletin1Details.setDocumentDate(bulletin1Details.getBulletinDate().toLocalDate());
            Bulletin1Details savedBulletin = bulletin1Repository.save(bulletin1Details);
            LOGGER.debug("Saved Bulletin1 entity: {}", savedBulletin);
            savedBulletin.setRefId(savedBulletin.getId());
            Bulletin1Details Bulletin = bulletin1Repository.save(savedBulletin);
            Bulletin1DetailsDTO responseDTO = Bulletin1DetailsMapper.toDTO(Bulletin);
            LOGGER.debug("Returning Bulletin DTO with ApplicationInstanceDTO: {}", responseDTO);
            responseDTO.setApplicationInstanceDTO(ApplicationInstanceDTO.builder()
                    .id(responseDTO.getId())
                    .applicationId(responseDTO.getApplicationId())
                    .applicationNo(responseDTO.getApplicationNo())
                    .currentStatus(responseDTO.getCurrentStatus())
                    .refId(responseDTO.getRefId())
                    .documentDate(Bulletin.getDocumentDate())
                    .build());
            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("Error in save Bulletin1: ", e);
            throw e;
        }
    }

    @Override
    public Bulletin1DetailsDTO updateBulletin1(Long id, Bulletin1DetailsDTO bulletin1DTO) {
        try {
            if (!bulletin1Repository.existsById(id)) {
                LOGGER.error("Bulletin1 not found with ID: ", id);
                throw new UserNotFoundException("Bulletin1 not found with ID: " + id);
            } else {
                LOGGER.info("Bulletin1 found with ID: ", id);
            }
            Bulletin1Details existingBulletin1 = bulletin1Repository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Bulletin1 Details not found with ID: " + id));
            LOGGER.info("Successfully fetched Bulletin1 with ID: {}", id);
            bulletin1DTO.setCurrentStatus("" + bulletin1DTO.getStatus());
            Bulletin1Details updatedBulletin1 = Bulletin1DetailsMapper.toEntity(bulletin1DTO);
            updatedBulletin1.setDocumentDate(updatedBulletin1.getBulletinDate().toLocalDate());
            updatedBulletin1.setId(id);
            updatedBulletin1 = bulletin1Repository.save(updatedBulletin1);
            LOGGER.debug("Updated bulletin1 entity with Application No: {}", updatedBulletin1);
            if (updatedBulletin1.getLokSabhaSessionNo() == null && updatedBulletin1.getLokSabhaNo() == null) {
                LOGGER.info(
                        "Saved Bulletin1 generate attributes: ID={}, RajyaSabhaSessionNo={},  Status={}",
                        updatedBulletin1.getId(), updatedBulletin1.getRajyaSabhaSessionNo(),
                        updatedBulletin1.getStatus());
            } else {
                LOGGER.info(
                        "Saved Bulletin1 generate attributes: ID={}, LokSabhaNo={}, SessionNo={},  Status={}",
                        updatedBulletin1.getId(), updatedBulletin1.getLokSabhaNo(),
                        updatedBulletin1.getLokSabhaSessionNo(), updatedBulletin1.getStatus());
            }
            return Bulletin1DetailsMapper.toDTO(updatedBulletin1);
        } catch (Exception e) {
            LOGGER.error("Error in save Bulletin1 : ", e);
            throw e;
        }
    }

    @Override
    public Bulletin1DetailsDTO getBulletin1ById(Long id) {
        Bulletin1Details entity = bulletin1Repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Bulletin1 Details  not found with ID: " + id));
        Bulletin1DetailsDTO dto = Bulletin1DetailsMapper.toDTO(entity);
        // if(entity.getLokSabhaSessionNo()!=null)
        // {
        // MastersLokSabhaSessionDTO
        // lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
        // dto.setLokSabhaSessionDetails(lokSabhaSession);
        // }
        // if(entity.getLokSabhaNo()!=null)
        // {
        // MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
        // dto.setLokSabhaDetails(lokSabha);
        // }
        // if(entity.getRajyaSabhaSessionNo()!=null)
        // {
        // MastersRajyaSabhaSessionDTO
        // rajyaSabhaSession=getRajyaSabhaSessionDetails(entity.getRajyaSabhaSessionNo());
        // dto.setRajyaSabhaSessionDetails(rajyaSabhaSession);
        // }
        // mastersRSSRepository
        // if (entity.getApproverId() != null) {
        // ApproverDTO approver = getApproverDetails(entity.getApproverId());
        // dto.setApproverDetails(approver);
        // }

        return dto;
    }

    // private ApproverDTO getApproverDetails(Long id) {
    // return userRepository.findById(id)
    // .map(ApproverMapper::toDTO) // Convert to DTO
    // .orElse(null);
    // }

    // private Bulletin1Docs createBulletinDocs(Bulletin1Docs entity) {
    // return bulletinDocsRepo.save(entity);
    // }

    @Override
    public List<Bulletin1DetailsDTO> getAllBulletin1s() {
        List<Bulletin1DetailsDTO> dtoList = new ArrayList<>();
        for (Bulletin1Details entity : bulletin1Repository.findAll()) {
            Bulletin1DetailsDTO dto = Bulletin1DetailsMapper.toDTO(entity);

            // if(entity.getLokSabhaSessionNo()!=null)
            // {
            // MastersLokSabhaSessionDTO
            // lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
            // dto.setLokSabhaSessionDetails(lokSabhaSession);
            // }
            // if(entity.getLokSabhaNo()!=null)
            // {
            // MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
            // dto.setLokSabhaDetails(lokSabha);
            // }
            // if(entity.getRajyaSabhaSessionNo()!=null)
            // {
            // MastersRajyaSabhaSessionDTO
            // rajyaSabhaSession=getRajyaSabhaSessionDetails(entity.getRajyaSabhaSessionNo());
            // dto.setRajyaSabhaSessionDetails(rajyaSabhaSession);
            // }
            // if(entity.getApproverId()!=null)
            // {
            // ApproverDTO approver=getApproverDetails(entity.getApproverId());
            // dto.setApproverDetails(approver);
            // }
            dtoList.add(dto);
        }
        return dtoList;
    }

    // private MastersLokSabhaSessionDTO getLokSabhaSessionDetails(Long id) {
    // return masterLSSRepository.findById(id)
    // .map(LokSabhaSessionMapper::toDTO) // Convert to DTO
    // .orElse(null); // Return null if not found
    // }
    // private MastersRajyaSabhaSessionDTO getRajyaSabhaSessionDetails(Long id) {
    // return mastersRSSRepository.findById(id)
    // .map(RajyaSabhaSessionMapper::toDTO) // Convert to DTO
    // .orElse(null);
    // }
    // private MastersLokSabhaDTO getLokSabhaDetails(Long id) {
    // return masterLSRepository.findById(id)
    // .map(LokSabhaMapper::toDTO) // Convert to DTO
    // .orElse(null); // Return null if not found
    // }
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
    public void setSessionId(Long userId, HttpSession session) {
        if (userId != null) {
            session.setAttribute(USER_ID_SESSION_ATTR, userId);
            LOGGER.info("User ID {} set in session.", userId);
        } else {
            LOGGER.warn("Attempted to set a null user ID in session.");
        }
    }

    @Override
    public List<Bulletin1DetailsDTO> getBulletin1s(Boolean isActive, Boolean draftStatus, Long lokSabhaNo,
            Long lokSabhaSessionNo, Long rajyaSabhaSessionNo, LocalDateTime startDate, LocalDateTime endDate,
            String status) {
        List<Bulletin1DetailsDTO> dtoList = new ArrayList<>();
        List<Bulletin1Details> entitys;
        if (draftStatus == null) {
            draftStatus = false;
        }
        if (isActive == null) {
            isActive = true;
        }
        Specification<Bulletin1Details> spec = Specification.where(null);
        spec = spec.and(Bulletin1Spec.isActive(isActive));
        spec = spec.and(Bulletin1Spec.hasDraftStatus(draftStatus));
        if (lokSabhaSessionNo != null)
            spec = spec.and(Bulletin1Spec.hasLokSabhaSessionNo(lokSabhaSessionNo));
        if (rajyaSabhaSessionNo != null)
            spec = spec.and(Bulletin1Spec.hasRajyaSabhaSessionNo(rajyaSabhaSessionNo));
        if (startDate != null || endDate != null)
            spec = spec.and(Bulletin1Spec.hasDateBetween(startDate, endDate));
        if (status != null)
            spec = spec.and(Bulletin1Spec.hasStatus(status));
        entitys = bulletin1Repository.findAll(spec);
        Long houseId = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsDTO) {
            houseId = ((UserDetailsDTO) principal).getHouseId();
        }
        for (Bulletin1Details entity : entitys) {
            if (houseId == 1) {
                if (entity.getRajyaSabhaSessionNo() != null) {
                    Bulletin1DetailsDTO dto = Bulletin1DetailsMapper.toDTO(entity);
                    dtoList.add(dto);
                }
            } else if (houseId == 2) {
                if (entity.getLokSabhaSessionNo() != null) {
                    Bulletin1DetailsDTO dto = Bulletin1DetailsMapper.toDTO(entity);
                    dtoList.add(dto);
                }
            }
        }
        return dtoList;
    }
    // //////////////////////////

    public String getTokenFromRequest() {
        String authorizationHeader = httpRequest.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public SerialNumberResponse generateApplicationNo(SerialNumberRequest request) {
        String url = masterServiceUrl + "/serial-number";
        String token = getTokenFromRequest();
        if (token == null) {
            throw new IllegalStateException("Authorization token is missing");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<SerialNumberRequest> entity = new HttpEntity<>(request, headers);
        try {
            // RestTemplate restTemplate = new RestTemplate();
            LOGGER.info("getting application no using Api Call",entity);

            return restTemplate.postForObject(url, entity, SerialNumberResponse.class);
        } catch (Exception e) {
            System.err.println("Error during API call: " + e.getMessage());
            LOGGER.error("Error in Api Call", e);
            throw e;
        }
    }

}
