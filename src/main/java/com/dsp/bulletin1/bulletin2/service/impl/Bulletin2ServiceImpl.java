package com.dsp.bulletin1.bulletin2.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dsp.bulletin1.bulletin2.dto.Bulletin2DTO;
import com.dsp.bulletin1.bulletin2.entity.Bulletin2;
import com.dsp.bulletin1.bulletin2.entity.entitySpecification.Bulletin2Spec;
import com.dsp.bulletin1.bulletin2.mapper.Bulletin2Mapper;
import com.dsp.bulletin1.bulletin2.repository.Bulletin2Repository;
import com.dsp.bulletin1.bulletin2.service.Bulletin2Service;
import com.dsp.bulletin1.dto.ApplicationInstanceDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaDTO;
import com.dsp.bulletin1.dto.MastersLokSabhaSessionDTO;
import com.dsp.bulletin1.entity.enums.Status;
import com.dsp.bulletin1.exception.ResourceNotFoundException;
import com.dsp.bulletin1.exception.UserNotFoundException;
import com.dsp.bulletin1.mapper.LokSabhaMapper;
import com.dsp.bulletin1.mapper.LokSabhaSessionMapper;
import com.dsp.bulletin1.repository.ApplicationDetailsRepository;
import com.dsp.bulletin1.repository.MasterBranchRepository;
import com.dsp.bulletin1.repository.MasterLSRepository;
import com.dsp.bulletin1.repository.MasterLSSRepository;
import com.dsp.bulletin1.repository.MyUserRepository;
import com.dsp.bulletin1.response.GenericResponse;
// import lombok.Value;
@Service
public class Bulletin2ServiceImpl implements Bulletin2Service {
	private static final Logger LOGGER = LogManager.getLogger(Bulletin2ServiceImpl.class);
	private static final String USER_ID_SESSION_ATTR = "userCreateId";
	private final Bulletin2Repository bulletin2Repository;
    private final MasterLSRepository masterLSRepository;
    private final MasterBranchRepository masterBranchRepository;
    private final MasterLSSRepository masterLSSRepository;
    private final ApplicationDetailsRepository applicationRepository;
    private final RestTemplate restTemplate;
	@Autowired
	public Bulletin2ServiceImpl( Bulletin2Repository bulletin2Repository, MasterLSRepository masterLSRepository,
    MasterBranchRepository masterBranchRepository,RestTemplate restTemplate, MasterLSSRepository masterLSSRepository, MyUserRepository userRepository,ApplicationDetailsRepository applicationRepository)
    {	this.applicationRepository=applicationRepository;
        this.bulletin2Repository = bulletin2Repository;
        this.masterBranchRepository=masterBranchRepository;
        this.masterLSRepository=masterLSRepository;
        this.masterLSSRepository=masterLSSRepository;
        this.restTemplate=restTemplate;
	}

	@Value("${application.instance.service.url}")
	private String applicationInstanceServiceUrl;
    
	@Value("${loksabha.session.service.url}")
	private String lokSabhaSessionServiceUrl;
    
	@Override
	public Bulletin2DTO createBulletin2(Bulletin2DTO bulletin2DTO) 
    {
        if(bulletin2DTO.getStatus()==null)
        {
            bulletin2DTO.setStatus(Status.IN_PROGRESS);
        }
        if(bulletin2DTO.getDraftStatus()==null)
        {
            bulletin2DTO.setDraftStatus(false);
        }

        Bulletin2 bulletin2 = Bulletin2Mapper.toEntity(bulletin2DTO);
        LOGGER.debug("Entering saveListOfBusinessCreate  with ListOfBusinessCreateDTO: {}", bulletin2);
		try { 
			LOGGER.debug("Converted lob DTO to entity: {}", bulletin2);
			Bulletin2 savedBulletin2 = bulletin2Repository.save(bulletin2);
			LOGGER.debug("Initial save of List of business to generate ID: {}", savedBulletin2);
            if(savedBulletin2.getLokSabhaSessionNo()==null)
            {
                String applicationNo = generateApplicationNo(savedBulletin2.getRajyaSabhaSessionNo(),
                 savedBulletin2.getId(),savedBulletin2.getDraftStatus());
                 savedBulletin2.setApplicationNo(applicationNo);
    			LOGGER.debug("Generated Application No for LOB RS: {}", applicationNo);
            }
			else{
                String applicationNo = generateApplicationNo(savedBulletin2.getLokSabhaNo(),
                savedBulletin2.getLokSabhaSessionNo(), savedBulletin2.getId(),savedBulletin2.getDraftStatus());
                savedBulletin2.setApplicationNo(applicationNo);
                LOGGER.debug("Generated Application No for LOB LS: {}", applicationNo);
            }
			savedBulletin2 = bulletin2Repository.save(savedBulletin2);
			LOGGER.debug("Saved List of Business entity with Application No: {}", savedBulletin2);
                if(savedBulletin2.getLokSabhaSessionNo()==null && savedBulletin2.getLokSabhaNo()==null)
                {
                    LOGGER.info(
                "Saved Lob generate attributes: ID={}, RajyaSabhaSessionNo={},  Status={}",
                savedBulletin2.getId(),  savedBulletin2.getRajyaSabhaSessionNo(), savedBulletin2.getStatus());
                }
                else{
                    LOGGER.info(
                        "Saved Lob generate attributes: ID={}, LokSabhaNo={}, SessionNo={},  Status={}",
                        savedBulletin2.getId(), savedBulletin2.getLokSabhaNo(), savedBulletin2.getLokSabhaSessionNo(),
                        savedBulletin2.getStatus());
                }
            ApplicationInstanceDTO applicationInstanceDTO = ApplicationInstanceDTO.builder()
                    .id(savedBulletin2.getId())
                    .applicationId(savedBulletin2.getId())
					.applicationNo(savedBulletin2.getApplicationNo()) 
                    .currentStatus(savedBulletin2.getCurrentStatus())
					.build();
			LOGGER.debug("Created ApplicationInstanceDTO for microservice call: {}", applicationInstanceDTO);

			// Call ApplicationInstance microservice to save the application instance data
			ApplicationInstanceDTO savedInstanceDTO = createApplicationInstance(applicationInstanceDTO);
			LOGGER.debug("Saved ApplicationInstanceDTO returned from microservice: {}", savedInstanceDTO);

			// Validate template ID
			// Convert saved entity back to DTO
			Bulletin2DTO responseDTO = Bulletin2Mapper.toDTO(savedBulletin2);
			responseDTO.setApplicationInstanceDTO(savedInstanceDTO); // Set the ApplicationInstanceDTO
			LOGGER.debug("Returning Lob DTO with ApplicationInstanceDTO: {}", responseDTO);

			//  responseDTO.setTemplateId(ListOfBusinessCreateDTO.getTemplateId()); 
			//  responseDTO.setTemplateHindiId(ListOfBusinessCreateDTO.getTemplateHindiId());

	            // LOGGER.debug("Returning Lob DTO with ApplicationInstanceDTO and Template ID: {}", responseDTO);

			return responseDTO;
		} catch (Exception e) {
			LOGGER.error("Error in saveListOfBusinessCreate: ", e);
			throw e;
		}
       	}
    private String generateApplicationNo(Long rajyaSabhaSessionNo, Long id,Boolean draftStatus) {
        if(draftStatus)
        {
        return "DLOBRS" + rajyaSabhaSessionNo + id;
        }
        else{
            return "LOBRS"+rajyaSabhaSessionNo+ id;
        }
    }
    private String generateApplicationNo(Long lokSabhaNo, Long sessionNo, Long id,Boolean draftStatus) {
		if(draftStatus)
        {
        return "DLOBLS" + lokSabhaNo + sessionNo + id;
        }
        else{
            return "LOBLS"+lokSabhaNo + sessionNo + id;
        }
	}
//updateByRefId
    private ApplicationInstanceDTO createApplicationInstance(ApplicationInstanceDTO applicationInstanceDTO) {
		// String url = applicationInstanceServiceUrl + "/create";
        String url = UriComponentsBuilder.fromHttpUrl(applicationInstanceServiceUrl)
                                 .path("/create")
                                 .toUriString();
		LOGGER.debug("Calling ApplicationInstance service at URL: {} with ApplicationInstanceDTO: {}", url,
				applicationInstanceDTO);
		try {
			ResponseEntity<GenericResponse<ApplicationInstanceDTO>> response = restTemplate.exchange(url,
					HttpMethod.POST, new HttpEntity<>(applicationInstanceDTO),
					new ParameterizedTypeReference<GenericResponse<ApplicationInstanceDTO>>() {
					});
			if (response == null || response.getBody() == null || response.getBody().getData() == null) {
				LOGGER.error("Response or body is null from ApplicationInstance service");
				throw new RuntimeException("Failed to create ApplicationInstance, response is null");
			}

			ApplicationInstanceDTO savedInstance = response.getBody().getData();
			LOGGER.debug("Received ApplicationInstanceDTO from ApplicationInstance service: {}", savedInstance);
			return savedInstance;

		} catch (ResourceNotFoundException e) {
			LOGGER.error("Resource not found when calling ApplicationInstance service: {}", e.getMessage());
			throw e; // Re-throw the exception for further handling
		}  catch (HttpStatusCodeException e) {
            // Log the status code and response body for debugging
            LOGGER.error("Error response from ApplicationInstance service: Status={}, Body={}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to create ApplicationInstance", e);
        }
        catch (Exception e) {
			LOGGER.error("Error calling ApplicationInstance service: ", e);
			throw new RuntimeException("Failed to create ApplicationInstance", e);
		}

	}
    private ApplicationInstanceDTO updateApplicationInstance(ApplicationInstanceDTO applicationInstanceDTO) {
		// String url = applicationInstanceServiceUrl + "/create";
        // String url = UriComponentsBuilder.fromHttpUrl(applicationInstanceServiceUrl)
        //                          .path("/updateByRefId/{}",applicationInstanceDTO.getRefId())
        //                          .toUriString();
        String url = UriComponentsBuilder.fromHttpUrl(applicationInstanceServiceUrl)
        .path("/getBy/{id}")
        .buildAndExpand(applicationInstanceDTO.getId())
        .toUriString();

		LOGGER.debug("Calling ApplicationInstance service at URL: {} with ApplicationInstanceDTO: {}", url,
				applicationInstanceDTO);

		try {
			ResponseEntity<GenericResponse<ApplicationInstanceDTO>> response = restTemplate.exchange(url,
					HttpMethod.POST, new HttpEntity<>(applicationInstanceDTO),
					new ParameterizedTypeReference<GenericResponse<ApplicationInstanceDTO>>() {
					});
			if (response == null || response.getBody() == null || response.getBody().getData() == null) {
				LOGGER.error("Response or body is null from ApplicationInstance service");
				throw new RuntimeException("Failed to Update ApplicationInstance, response is null");
			}

			ApplicationInstanceDTO savedInstance = response.getBody().getData();
			LOGGER.debug("Received ApplicationInstanceDTO from ApplicationInstance service: {}", savedInstance);
			return savedInstance;

		} catch (ResourceNotFoundException e) {
			LOGGER.error("Resource not found when calling ApplicationInstance service: {}", e.getMessage());
			throw e; // Re-throw the exception for further handling
		}  catch (HttpStatusCodeException e) {
            // Log the status code and response body for debugging
            LOGGER.error("Error response from ApplicationInstance service: Status={}, Body={}",
                e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to Update ApplicationInstance", e);
        }
        catch (Exception e) {
			LOGGER.error("Error calling ApplicationInstance service: ", e);
			throw new RuntimeException("Failed to update ApplicationInstance", e);
		}

	}
	@Override
	public Bulletin2DTO updateById(Long id, Bulletin2DTO lobDTO) 
    {
        try{
            if (!bulletin2Repository.existsById(id)) {
            LOGGER.error("Bulletin2 not found with ID: " ,id);
			throw new UserNotFoundException("Bulletin2 not found with ID: " + id);
            }
            else{
                LOGGER.info("Bulletin2 found with ID: " ,id);
            }
            LOGGER.info("Attempting to fetch Bulletin2 with ID: {}", id);
            Bulletin2 existingBulletin2 = bulletin2Repository.findById(id)
            .orElseThrow(() -> {
                LOGGER.error("Bulletin2 not found with ID: {}", id);
                return new UserNotFoundException("Bulletin2 not found with ID: " + id);
            });
            LOGGER.info("Successfully fetched Bulletin2 with ID: {}", id);
            Bulletin2 updatedBulletin2 = Bulletin2Mapper.toEntity(lobDTO);
            updatedBulletin2.setId(existingBulletin2.getId());
            Bulletin2 updatedEntityBulletin2=bulletin2Repository.save(updatedBulletin2);
            if(updatedEntityBulletin2.getApplicationNo()!=null || updatedEntityBulletin2.getApplicationNo().startsWith("D"))
            {
                if(updatedEntityBulletin2.getLokSabhaSessionNo()==null)
                {
                    String applicationNo = generateApplicationNo(updatedEntityBulletin2.getRajyaSabhaSessionNo(), 
                    updatedEntityBulletin2.getId(),updatedEntityBulletin2.getDraftStatus());
                    updatedEntityBulletin2.setApplicationNo(applicationNo);
                    LOGGER.debug("Generated Application No for Bulletin2 RS: {}", applicationNo);
                }
                else{
                    String applicationNo = generateApplicationNo(updatedEntityBulletin2.getLokSabhaNo(),
                    updatedEntityBulletin2.getLokSabhaSessionNo(), updatedEntityBulletin2.getId(),updatedEntityBulletin2.getDraftStatus());
                    updatedEntityBulletin2.setApplicationNo(applicationNo);
                    LOGGER.debug("Generated Application No for Bulletin2 LS: {}", applicationNo);
                }
            }
            updatedEntityBulletin2 = bulletin2Repository.save(updatedEntityBulletin2);
                LOGGER.debug("Updated Bulletin2 entity with Application No: {}", updatedEntityBulletin2);
                if(updatedEntityBulletin2.getLokSabhaSessionNo()==null && updatedEntityBulletin2.getLokSabhaNo()==null)
                {
                    LOGGER.info(
                "Saved Bulletin2 generate attributes: ID={}, RajyaSabhaSessionNo={},  Status={}",
                updatedEntityBulletin2.getId(),  updatedEntityBulletin2.getRajyaSabhaSessionNo(), 
                updatedEntityBulletin2.getStatus());
                }
                else{
                    LOGGER.info(
                        "Saved Lob generate attributes: ID={}, LokSabhaNo={}, SessionNo={},  Status={}",
                        updatedEntityBulletin2.getId(), updatedEntityBulletin2.getLokSabhaNo(), 
                        updatedEntityBulletin2.getLokSabhaSessionNo(), updatedEntityBulletin2.getStatus());
                }
            ApplicationInstanceDTO applicationInstanceDTO = ApplicationInstanceDTO.builder()
                    .id(updatedEntityBulletin2.getId())
                    .applicationId(updatedEntityBulletin2.getId())
					.applicationNo(updatedEntityBulletin2.getApplicationNo()) 
                    .currentStatus(updatedEntityBulletin2.getCurrentStatus())
                	.build();
			LOGGER.debug("Updated ApplicationInstanceDTO for microservice call: {}", applicationInstanceDTO);

			// Call ApplicationInstance microservice to save the application instance data
			ApplicationInstanceDTO savedInstanceDTO = updateApplicationInstance(applicationInstanceDTO);
			LOGGER.debug("Updated ApplicationInstanceDTO returned from microservice: {}", savedInstanceDTO);

			// Validate template ID
			// Convert saved entity back to DTO
			Bulletin2DTO responseDTO = Bulletin2Mapper.toDTO(updatedEntityBulletin2);
			responseDTO.setApplicationInstanceDTO(savedInstanceDTO); // Set the ApplicationInstanceDTO
			LOGGER.debug("Returning Bulletin2 DTO with ApplicationInstanceDTO: {}", responseDTO);
			//  responseDTO.setTemplateId(ListOfBusinessCreateDTO.getTemplateId()); 
			//  responseDTO.setTemplateHindiId(ListOfBusinessCreateDTO.getTemplateHindiId());
            // LOGGER.debug("Returning Lob DTO with ApplicationInstanceDTO and Template ID: {}", responseDTO);
			return responseDTO;
		} catch (Exception e) {
			LOGGER.error("Error in save Bulletin2: ", e);
			throw e;
		}
		// return ListOfBusinessCreateMapper.toDTO(updatedLobCreate);
	}
    
	@Override
	public Bulletin2DTO getById(Long id) {
        if (!bulletin2Repository.existsById(id)) {
            LOGGER.error("Bulletin2 not found with ID: " ,id);
			throw new UserNotFoundException("Bulletin2 not found with ID: " + id);
		}
        else{
            LOGGER.info("Bulletin2 found with ID: " ,id);
        }
        LOGGER.debug("Entering get Bulletin2 ById with ID: {}", id);

		try {
			Bulletin2 entity = bulletin2Repository.findById(id).orElseThrow(() -> {
				LOGGER.error("Bulletin2 not found with ID: {}", id);
				return new ResourceNotFoundException("Bulletin2 not found with id: " + id);
			});

			Bulletin2DTO dto = Bulletin2Mapper.toDTO(entity);
            if(entity.getApplicationNo()!=null)
			{
                ApplicationInstanceDTO instanceDTO = fetchApplicationInstanceData(entity.getApplicationNo());
			    dto.setApplicationInstanceDTO(instanceDTO);
            }
			LOGGER.debug("Returning existingLobCreateDTO: {}", dto);            

        if(entity.getLokSabhaSessionNo()!=null)
        {
            MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
            dto.setLokSabhaSessionNoDetails(lokSabhaSession);  
        }
        if(entity.getLokSabhaNo()!=null)
        {
            MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
            dto.setLokSabhaNoDetails(lokSabha);
        }
            // if(entity.getApplicationNo()!=null)
            // {
            //     ApplicationDTO application=getApplicationDetails(entity.getApplicationNo());
            //     lobCreateDTO.setApplicationName(application);  
            // }

		return dto;
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Resource not found when retrieving lob by id: {}", e.getMessage());
			throw e; // Re-throw the exception for further handling
		} catch (Exception e) {
			LOGGER.error("Error in getLobById: ", e);
			throw new RuntimeException("Failed to retrieve lob by ID", e);
		}
	}

	private ApplicationInstanceDTO fetchApplicationInstanceData(String applicationNo) {
		String url = applicationInstanceServiceUrl + "/getApplicationNo/" + applicationNo;
		LOGGER.debug("Fetching ApplicationInstance by applicationNo from URL: {}", url);

		try {
			ResponseEntity<GenericResponse<ApplicationInstanceDTO>> response = restTemplate.exchange(url,
					HttpMethod.GET, null, 
                    new ParameterizedTypeReference<GenericResponse<ApplicationInstanceDTO>>() {
					});

			if (response == null || response.getBody() == null || response.getBody().getData() == null) {
				LOGGER.error("Response or body is null when fetching ApplicationInstance for applicationNo: {}",
						applicationNo);
				throw new RuntimeException("Failed to fetch ApplicationInstance, response is null");
			}

			ApplicationInstanceDTO instanceDTO = response.getBody().getData();
			LOGGER.debug("Fetched ApplicationInstanceDTO: {}", instanceDTO);
			return instanceDTO;

		} catch (ResourceNotFoundException e) {
			LOGGER.error("Resource not found for applicationNo: {}", applicationNo);
			throw e; // Re-throw the exception for further handling
		} catch (Exception e) {
			LOGGER.error("Error fetching ApplicationInstance for applicationNo: {}", applicationNo, e);
			throw new RuntimeException("Failed to fetch ApplicationInstance data", e);
		}
	}
    // 	@Override
	// public List<Bulletin2DTO> getAllBulletins() 
    // {   
    //     LOGGER.debug("Entering getAllLOBs");
	// 	try {
    //         List<Bulletin2> entitys = bulletin2Repository.findAll();
	// 		LOGGER.debug("Fetched all LOBs from database: {}", entitys);
	// 		List<Bulletin2DTO> responseList = new ArrayList<>();
	// 		for (Bulletin2 entity : bulletin2Repository.findAll()) 
    //         {
	// 		Bulletin2DTO dto = Bulletin2Mapper.toDTO(entity);
    //         if(entity.getLokSabhaSessionNo()!=null)
    //         {
    //             MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
    //             dto.setLokSabhaSessionNoDetails(lokSabhaSession);  
    //         }
    //         if(entity.getLokSabhaNo()!=null)
    //         {
    //         MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
    //         dto.setLokSabhaNoDetails(lokSabha);
    //         }
    //         if(entity.getApplicationNo()!=null)
    //         {
    //         ApplicationInstanceDTO instanceDTO = fetchApplicationInstanceData(entity.getApplicationNo());
    //         dto.setApplicationInstanceDTO(instanceDTO);
    //         }
    //         responseList.add(dto);
    //     }
    //     LOGGER.debug("Returning all Lobs with ApplicationInstanceDTOs: {}", responseList);
    //     return responseList;
	// 	} catch (Exception e) {
	// 		LOGGER.error("Error in getAllSummonNotifications: ", e);
	// 		throw new RuntimeException("Failed to retrieve SummonNotifications", e);
	// 	}
    // }


    // private ApplicationDTO getApplicationDetails(Long id) {
    //     return applicationRepository.findById(id)
    //         .map(ApplicationMapper::toDTO)  
    //         .orElseGet(() -> {
    //             LOGGER.warn("No branch found for ID: {}", id);  // Log if no branch is found
    //             return null;
    //         });
    // }  
    // private MastersBranchDTO getBranchDetails(Long id) {
    //     return masterBranchRepository.findById(id)
    //     .map(branch -> {
    //         LOGGER.debug("Branch found for ID: {}. Mapping to DTO.", id);
    //         return BranchMapper.toDTO(branch);  // Log within the mapping step
    //     })
    //     .orElseGet(() -> {
    //         LOGGER.warn("No branch found for ID: {}", id);  // Log if no branch is found
    //         return null;
    //     });
    // }
    private MastersLokSabhaSessionDTO getLokSabhaSessionDetails(Long id) {
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
		if (!bulletin2Repository.existsById(id)) {
            LOGGER.error("Bulletin2 not found with ID: " ,id);
			throw new UserNotFoundException("Bulletin2 not found with ID: " + id);
		}
        else{
            LOGGER.info("List Of Business found with ID: " ,id);
        }
		bulletin2Repository.deleteById(id);
	}
    @Override
    public List<Bulletin2DTO> getFilteredBulletins(Boolean isActive, Boolean draftStatus, Long lokSabhaNo,
     Long lokSabhaSessionNo, Long rajyaSabhaSessionNo,LocalDate startDate,LocalDate endDate) 
    {
        List<Bulletin2DTO> dtoList = new ArrayList<>();
        List<Bulletin2> entitys;
        if(draftStatus==null)
        {
            draftStatus=false;
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
            entitys = bulletin2Repository.findAll(Specification.where(Bulletin2Spec.isActive(isActive))
            .and(Bulletin2Spec.hasDraftStatus(draftStatus))
            .and(Bulletin2Spec.hasDateBetween(startDate, endDate))
            .and(Bulletin2Spec.hasLokSabhaSessionNo(lokSabhaSessionNo))).stream()
            // .map(ListOfBusinessCreateMapper::toDTO)
            .collect(Collectors.toList());
        }
        else if(lokSabhaSessionNo!=null) 
        {     
            entitys = bulletin2Repository.findAll(Specification.where(Bulletin2Spec.isActive(isActive))
            .and(Bulletin2Spec.hasDraftStatus(draftStatus))
            .and(Bulletin2Spec.hasLokSabhaSessionNo(lokSabhaSessionNo))).stream()
            // .map(ListOfBusinessCreateMapper::toDTO)
            .collect(Collectors.toList());
        }
        else if(rajyaSabhaSessionNo!=null){
            entitys= bulletin2Repository.findAll(Specification.where(Bulletin2Spec.isActive(isActive))
            .and(Bulletin2Spec.hasDraftStatus(draftStatus))
            .and(Bulletin2Spec.hasRajyaSabhaSessionNo(rajyaSabhaSessionNo))).stream()
            // .map(ListOfBusinessCreateMapper::toDTO)
            .collect(Collectors.toList());
        }
        else
        {
            entitys= bulletin2Repository.findAll(Specification.where(Bulletin2Spec.isActive(isActive))
            .and(Bulletin2Spec.hasDraftStatus(draftStatus))).stream().collect(Collectors.toList());
        }
        for (Bulletin2 entity : entitys) 
        {
            Bulletin2DTO dto = Bulletin2Mapper.toDTO(entity);         
            if(entity.getLokSabhaSessionNo()!=null)
            {
                MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
                dto.setLokSabhaSessionNoDetails(lokSabhaSession);  
            }
            if(entity.getLokSabhaNo()!=null)
            {
            MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
            dto.setLokSabhaNoDetails(lokSabha);
            }  
            // if(entity.getApplicationNo()!=null)
            // {
            // ApplicationDTO application=getApplicationDetails(entity.getApplicationNo());
            // dto.setApplicationName(application);  
            // }
            dtoList.add(dto);
        }
        return dtoList;
    }
    private Long getCurrentLokSabhaSession() {
        // /lokSabhaSessionServiceUrl
        String url = lokSabhaSessionServiceUrl + "/latest-session";
		LOGGER.debug("Fetching Current Session of LokSabha from URL: {}", url);
    try {
        //Map<String, List<?>>
        // ResponseEntity<GenericResponse<MastersLokSabhaSessionDTO>> response =
        ParameterizedTypeReference<Map<String, List<?>>> typeRef =
                new ParameterizedTypeReference<Map<String, List<?>>>() {};
        Map<String, List<?>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                typeRef
        ).getBody(); 
        if (response == null) {
        LOGGER.error("Response or body is null when fetching current session :{}",response);
            throw new RuntimeException("Failed to fetch ApplicationInstance, response is null");
        }
        System.out.println(response);
         Long sessionNo = (Long) response.get("session").get(1);
        // LOGGER.debug("Fetched ApplicationInstanceDTO: {}", instanceDTO);
        // return instanceDTO;
        return sessionNo;

    } catch (ResourceNotFoundException e) {
        LOGGER.error("Resource not found for LokSabhaSession");
        throw e; // Re-throw the exception for further handling
    } catch (Exception e) {
        LOGGER.error("Error fetching ApplicationInstance for applicationNo: {}", e);
        throw new RuntimeException("Failed to fetch ApplicationInstance data", e);
    }
    }
   
} 