package com.dsp.bulletin1.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dsp.bulletin1.dto.ApplicationInstanceDTO;
import com.dsp.bulletin1.dto.ApproverDTO;
import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.entity.Bulletin1Details;
import com.dsp.bulletin1.entity.Bulletin1Docs;
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

        System.out.println("*****************");
        if (bulletin1DTO.getDraftStatus() == null || (!bulletin1DTO.getDraftStatus())) {
            bulletin1DTO.setDraftStatus(false);
            if (bulletin1DTO.getLokSabhaSessionNo() == null && bulletin1DTO.getLokSabhaNo() == null) {
                String refNo = generateApplicationNo(bulletin1DTO.getRajyaSabhaSessionNo(), bulletin1DTO.getId(),
                        bulletin1DTO.getDraftStatus());
                bulletin1DTO.setRefNo(refNo);
                bulletin1DTO.setApplicationNo(refNo);
            } else {
                String refNo = generateApplicationNo(bulletin1DTO.getLokSabhaNo(),
                        bulletin1DTO.getLokSabhaSessionNo(), bulletin1DTO.getId(), bulletin1DTO.getDraftStatus());
                bulletin1DTO.setRefNo(refNo);
                bulletin1DTO.setApplicationNo(refNo);
            }
        } else {
            bulletin1DTO.setStatus(Status.DRAFTED);
            if (bulletin1DTO.getLokSabhaSessionNo() == null && bulletin1DTO.getLokSabhaNo() == null) {
                String refNo = generateApplicationNo(bulletin1DTO.getRajyaSabhaSessionNo(), bulletin1DTO.getId(),
                        bulletin1DTO.getDraftStatus());
                bulletin1DTO.setRefNo(refNo);
                bulletin1DTO.setApplicationNo(refNo);
            } else {
                String refNo = generateApplicationNo(bulletin1DTO.getLokSabhaNo(),
                        bulletin1DTO.getLokSabhaSessionNo(), bulletin1DTO.getId(), bulletin1DTO.getDraftStatus());
                bulletin1DTO.setRefNo(refNo);
                bulletin1DTO.setApplicationNo(refNo);
            }
        }
        if (bulletin1DTO.getStatus() == null) {
            bulletin1DTO.setStatus(Status.SUBMITTED);
        }
        bulletin1DTO.setCurrentStatus("" + bulletin1DTO.getStatus());
        System.out.println("insertion bulletin1");
        System.out.println(bulletin1DTO);
        Bulletin1Details bulletin1Details = Bulletin1DetailsMapper.toEntity(bulletin1DTO);
        LOGGER.debug("Entering save Bulletin1  with Bulletin1 DTO: {}", bulletin1Details);
        try {
            LOGGER.debug("Converted Bulletin1 DTO to entity: {}", bulletin1Details);
            Bulletin1Details savedBulletin = bulletin1Repository.save(bulletin1Details);
            LOGGER.debug("Saved Bulletin1 entity: {}", savedBulletin);
            System.out.println("before Appli no generation");
            System.out.println(savedBulletin);
            savedBulletin.setRefId(savedBulletin.getId());

            if (savedBulletin.getLokSabhaSessionNo() == null && savedBulletin.getLokSabhaNo() == null) {
                String refNo = generateApplicationNo(savedBulletin.getRajyaSabhaSessionNo(), savedBulletin.getId(),
                        savedBulletin.getDraftStatus());
                savedBulletin.setRefNo(refNo);
                savedBulletin.setApplicationNo(refNo);
                LOGGER.info(
                        "Saved Bulletin1 attributes: ID={}, RajyaSabhaSessionNo={},  Status={}",
                        savedBulletin.getId(), savedBulletin.getRajyaSabhaSessionNo(), savedBulletin.getStatus());
            } else {
                String refNo = generateApplicationNo(savedBulletin.getLokSabhaNo(),
                        savedBulletin.getLokSabhaSessionNo(), savedBulletin.getId(), savedBulletin.getDraftStatus());
                savedBulletin.setRefNo(refNo);
                savedBulletin.setApplicationNo(refNo);
                LOGGER.info(
                        "Saved Bulletin1 attributes: ID={}, LokSabhaNo={}, SessionNo={},  Status={}",
                        savedBulletin.getId(), savedBulletin.getLokSabhaNo(),

                        savedBulletin.getLokSabhaSessionNo(), savedBulletin.getStatus());
                savedBulletin.setRefNo(refNo);
            }
            savedBulletin.setCurrentStatus("" + savedBulletin.getStatus());

            System.out.println("after generating applno");
            System.out.println(savedBulletin);
            Bulletin1Details Bulletin = bulletin1Repository.save(savedBulletin);

            // Bulletin1DocsDTO docsDTO= Bulletin1DocsDTO.builder()
            // .id(Bulletin.getId())
            // .refDocDate(Bulletin.getBulletinDate())
            // .refDocId(Bulletin.getCodeId())
            // .bulletin1Id(Bulletin.getId())
            // .build();
            // Bulletin1Docs entity=Bulletin1DocsMapper.toEntity(docsDTO);
            // LOGGER.debug("Created docsDTO for microservice call: {}", entity);
            // Bulletin1Docs savedEntity = createBulletinDocs(entity);
            // Bulletin1DocsDTO savedDocsDto=Bulletin1DocsMapper.toDTO(savedEntity);
            // LOGGER.debug("Saved BulletinDocs returned from microservice: {}",
            // savedEntity);
            Bulletin1DetailsDTO responseDTO = Bulletin1DetailsMapper.toDTO(Bulletin);
            // responseDTO.setDocDetails(savedDocsDto);
            LOGGER.debug("Returning Bulletin DTO with ApplicationInstanceDTO: {}", responseDTO);
            responseDTO.setApplicationInstanceDTO(ApplicationInstanceDTO.builder()
                    .id(responseDTO.getId())
                    .applicationId(responseDTO.getApplicationId())
                    .applicationNo(responseDTO.getApplicationNo())
                    .currentStatus(responseDTO.getCurrentStatus())
                    .refId(responseDTO.getRefId())
                    .build());
            return responseDTO;
        } catch (Exception e) {
            LOGGER.error("Error in save Bulletin1: ", e);
            throw e;
        }
        // Bulletin1Details bulletin1Details =
        // Bulletin1DetailsMapper.toEntity(bulletin1DTO);
        // bulletin1Details = bulletin1Repository.save(bulletin1Details);
        // return Bulletin1DetailsMapper.toDTO(bulletin1Details);
    }

    private String generateApplicationNo(Long rajyaSabhaSessionNo, Long id, Boolean saveAsDraft) {
        if (saveAsDraft) {
            return "DB1LS" + rajyaSabhaSessionNo + id;
        } else {
            return "B1RS" + rajyaSabhaSessionNo + id;
        }
    }

    private String generateApplicationNo(Long lokSabhaNo, Long sessionNo, Long id, Boolean saveAsDraft) {
        if (saveAsDraft) {
            return "DB1LS" + lokSabhaNo + sessionNo + id;
        } else {
            return "B1LS" + lokSabhaNo + sessionNo + id;
        }
    }

    @Override
    public Bulletin1DetailsDTO updateBulletin1(Long id, Bulletin1DetailsDTO bulletin1DTO) {
        System.out.println("!!!!!!!!!!");
        System.out.println(bulletin1DTO);
        System.out.println("id::::::::");
        System.out.println(id);
        try {
            if (!bulletin1Repository.existsById(id)) {
                System.out.println("Inside exists by id bulletin1");
                LOGGER.error("Bulletin1 not found with ID: ", id);
                throw new UserNotFoundException("Bulletin1 not found with ID: " + id);
            } else {
                LOGGER.info("Bulletin1 found with ID: ", id);
            }
            Bulletin1Details existingBulletin1 = bulletin1Repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Bulletin1 Details not found with ID: " + id));
            LOGGER.info("Successfully fetched Bulletin1 with ID: {}", id);
                System.out.println("bulletin1 in update");
                System.out.println(existingBulletin1);
            // if (existingBulletin1.getDraftStatus()) {
            //     bulletin1DTO.setStatus(Status.DRAFTED);
            //     if (bulletin1DTO.getLokSabhaSessionNo() == null && bulletin1DTO.getLokSabhaNo() == null) 
            //     {
            //         String refNo = generateApplicationNo(bulletin1DTO.getRajyaSabhaSessionNo(), bulletin1DTO.getId(),
            //                 bulletin1DTO.getDraftStatus());
            //         bulletin1DTO.setApplicationNo(refNo);
            //     } else {
            //         String refNo = generateApplicationNo(bulletin1DTO.getLokSabhaNo(),
            //                 bulletin1DTO.getLokSabhaSessionNo(), bulletin1DTO.getId(), bulletin1DTO.getDraftStatus());
            //         bulletin1DTO.setApplicationNo(refNo);
            //     }
            // }

            if (bulletin1DTO.getStatus() == null) {
                bulletin1DTO.setStatus(Status.In_Progress);
            }
            bulletin1DTO.setCurrentStatus(""+bulletin1DTO.getStatus());
            Bulletin1Details updatedBulletin1 = Bulletin1DetailsMapper.toEntity(bulletin1DTO);
            System.out.println("before applno generation bulletin1");
            System.out.println(updatedBulletin1);
            updatedBulletin1.setId(id);
            // if (updatedBulletin1.getApplicationNo() != null || 
            // updatedBulletin1.getApplicationNo().startsWith("D")) 
            // {
                if (updatedBulletin1.getLokSabhaSessionNo() == null) 
                {
                String applicationNo = generateApplicationNo(updatedBulletin1.getRajyaSabhaSessionNo(),
                        updatedBulletin1.getId(), updatedBulletin1.getDraftStatus());
                updatedBulletin1.setApplicationNo(applicationNo);
                updatedBulletin1.setRefNo(applicationNo);
                LOGGER.debug("Generated Application No for Bulletin1 RS: {}", applicationNo);
                } 
                else 
                {
                String applicationNo = generateApplicationNo(updatedBulletin1.getLokSabhaNo(),
                updatedBulletin1.getLokSabhaSessionNo(), updatedBulletin1.getId(),
                updatedBulletin1.getDraftStatus());
                updatedBulletin1.setApplicationNo(applicationNo);
                updatedBulletin1.setRefNo(applicationNo);
                LOGGER.debug("Generated Application No for Bulletin1 LS: {}", applicationNo);
            // }
            }
            // updatedBulletin1.setId(existingBulletin1.getId());
            updatedBulletin1.setRefId(existingBulletin1.getId());
            System.out.println("before updating bulletin1");
            System.out.println(updatedBulletin1);
            System.out.println("before saving bulletin1");
            updatedBulletin1=bulletin1Repository.save(updatedBulletin1);
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
        if (entity.getApproverId() != null) {
            ApproverDTO approver = getApproverDetails(entity.getApproverId());
            dto.setApproverDetails(approver);
        }

        return dto;
    }

    private ApproverDTO getApproverDetails(Long id) {
        return userRepository.findById(id)
                .map(ApproverMapper::toDTO) // Convert to DTO
                .orElse(null);
    }

    private Bulletin1Docs createBulletinDocs(Bulletin1Docs entity) {
        return bulletinDocsRepo.save(entity);
    }

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
            Long lokSabhaSessionNo, Long rajyaSabhaSessionNo, LocalDateTime startDate, LocalDateTime endDate) {
        List<Bulletin1DetailsDTO> dtoList = new ArrayList<>();
        List<Bulletin1Details> entitys;
        System.out.println("draft status");
        System.out.println(draftStatus);
        if (draftStatus == null) {
            draftStatus = false;
        }
        if (isActive == null) {
            isActive = true;
        }
        // if(rajyaSabhaSessionNo==null)
        // {
        // rajyaSabhaSessionNo=getCurrentRajyaSabhaSession();
        // }
        // if(lokSabhaSessionNo==null)
        // {
        // lokSabhaSessionNo=getCurrentLokSabhaSession();
        // }
        if (startDate != null || endDate != null) {
            if (lokSabhaNo != null && lokSabhaSessionNo != null) {
                entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                        .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                        .and(Bulletin1Spec.hasDateBetween(startDate, endDate))
                        .and(Bulletin1Spec.hasLokSabhaNo(lokSabhaNo))
                        .and(Bulletin1Spec.hasLokSabhaSessionNo(lokSabhaSessionNo))).stream()
                        .collect(Collectors.toList());
            } else if (lokSabhaNo == null && lokSabhaSessionNo != null) {
                entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                        .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                        .and(Bulletin1Spec.hasDateBetween(startDate, endDate))
                        .and(Bulletin1Spec.hasLokSabhaSessionNo(lokSabhaSessionNo))).stream()
                        .collect(Collectors.toList());
            } else if (lokSabhaSessionNo == null && lokSabhaNo != null) {

                entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                        .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                        .and(Bulletin1Spec.hasDateBetween(startDate, endDate))
                        .and(Bulletin1Spec.hasLokSabhaNo(lokSabhaNo))
                // .and(Bulletin1Spec.hasLokSabhaSessionNo(lokSabhaSessionNo))
                ).stream()
                        .collect(Collectors.toList());

            } else {
                if (rajyaSabhaSessionNo != null) {
                    entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                            .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                            .and(Bulletin1Spec.hasDateBetween(startDate, endDate))
                            .and(Bulletin1Spec.hasRajyaSabhaSessionNo(rajyaSabhaSessionNo))).stream()
                            .collect(Collectors.toList());
                } else {
                    entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                            .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                            .and(Bulletin1Spec.hasDateBetween(startDate, endDate))).stream()
                            .collect(Collectors.toList());
                }
            }

        } else {
            if (lokSabhaNo != null && lokSabhaSessionNo != null) {
                entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                        .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                        .and(Bulletin1Spec.hasDateBetween(startDate, endDate))
                        .and(Bulletin1Spec.hasLokSabhaNo(lokSabhaNo))
                        .and(Bulletin1Spec.hasLokSabhaSessionNo(lokSabhaSessionNo))).stream()
                        .collect(Collectors.toList());
            } else if (lokSabhaNo == null && lokSabhaSessionNo != null) {
                entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                        .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                        .and(Bulletin1Spec.hasLokSabhaSessionNo(lokSabhaSessionNo))).stream()
                        .collect(Collectors.toList());
            } else if (lokSabhaSessionNo == null && lokSabhaNo != null) {
                System.out.println("-----loksabhano---------");
                System.out.println(lokSabhaNo);
                System.out.println("-----------");
                System.out.println(lokSabhaSessionNo);
                entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                        .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                        .and(Bulletin1Spec.hasLokSabhaNo(lokSabhaNo))
                // .and(Bulletin1Spec.hasLokSabhaSessionNo(lokSabhaSessionNo))
                ).stream()
                        .collect(Collectors.toList());
                System.out.println("ffffffffffffff");
                System.out.println(entitys);
            } else {
                if (rajyaSabhaSessionNo != null) {
                    entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                            .and(Bulletin1Spec.hasDraftStatus(draftStatus))
                            .and(Bulletin1Spec.hasRajyaSabhaSessionNo(rajyaSabhaSessionNo))).stream()
                            .collect(Collectors.toList());
                } else {
                    System.out.println("getAll active and status true or false");
                    entitys = bulletin1Repository.findAll(Specification.where(Bulletin1Spec.isActive(isActive))
                            .and(Bulletin1Spec.hasDraftStatus(draftStatus))).stream().collect(Collectors.toList());
                }
            }

        }
        for (Bulletin1Details entity : entitys) {
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

            dtoList.add(dto);
        }
        return dtoList;
    }
    // private Long getCurrentLokSabhaSession() {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method
    // 'getCurrentRajyaSabhaSession'");
    // }

}
