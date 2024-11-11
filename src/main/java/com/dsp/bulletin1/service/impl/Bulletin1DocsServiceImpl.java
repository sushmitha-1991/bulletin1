// package com.dsp.bulletin1.service.impl;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.stereotype.Service;

// import com.dsp.bulletin1.dto.ApplicationDTO;
// import com.dsp.bulletin1.dto.Bulletin1DocsDTO;
// import com.dsp.bulletin1.dto.MastersBranchDTO;
// import com.dsp.bulletin1.dto.MastersLokSabhaDTO;
// import com.dsp.bulletin1.dto.MastersLokSabhaSessionDTO;
// import com.dsp.bulletin1.entity.Bulletin1Docs;
// import com.dsp.bulletin1.entity.entitySpecification.Bulletin1DocsSpec;
// import com.dsp.bulletin1.exception.UserNotFoundException;
// import com.dsp.bulletin1.mapper.ApplicationMapper;
// import com.dsp.bulletin1.mapper.BranchMapper;
// import com.dsp.bulletin1.mapper.Bulletin1DocsMapper;
// import com.dsp.bulletin1.mapper.LokSabhaMapper;
// import com.dsp.bulletin1.mapper.LokSabhaSessionMapper;
// import com.dsp.bulletin1.repository.ApplicationDetailsRepository;
// import com.dsp.bulletin1.repository.Bulletin1DocsRepository;
// import com.dsp.bulletin1.repository.MasterBranchRepository;
// import com.dsp.bulletin1.repository.MasterLSRepository;
// import com.dsp.bulletin1.repository.MasterLSSRepository;
// import com.dsp.bulletin1.repository.MyUserRepository;
// import com.dsp.bulletin1.service.Bulletin1DocsService;

// import jakarta.servlet.http.HttpSession;
// @Service
// public class Bulletin1DocsServiceImpl implements Bulletin1DocsService {
// 	private static final Logger LOGGER = LoggerFactory.getLogger(Bulletin1DocsServiceImpl.class);
// 	private static final String USER_ID_SESSION_ATTR = "userId";
// 	private final Bulletin1DocsRepository bulletin1Repository;
//     private final MasterLSRepository masterLSRepository;
//     private final MasterBranchRepository masterBranchRepository;
//     private final MasterLSSRepository masterLSSRepository;
//     private final ApplicationDetailsRepository applicationRepository;
// 	@Autowired
// 	public Bulletin1DocsServiceImpl( Bulletin1DocsRepository bulletin1Repository, MasterLSRepository masterLSRepository,
//     MasterBranchRepository masterBranchRepository,MasterLSSRepository masterLSSRepository, MyUserRepository userRepository,ApplicationDetailsRepository applicationRepository)
//     {	this.applicationRepository=applicationRepository;
//         this.bulletin1Repository = bulletin1Repository;
//         this.masterBranchRepository=masterBranchRepository;
//         this.masterLSRepository=masterLSRepository;
//         this.masterLSSRepository=masterLSSRepository;
// 	}

// 	@Override
// 	public Bulletin1DocsDTO createBulletin1(Bulletin1DocsDTO bulletin1DTO) 
//     {
// 		Bulletin1Docs bulletin1Docs = Bulletin1DocsMapper.toEntity(bulletin1DTO);
// 		validateRequiredFields(bulletin1DTO);
// 		bulletin1Docs = bulletin1Repository.save(bulletin1Docs);
// 		return Bulletin1DocsMapper.toDTO(bulletin1Docs);
// 	}
// 	@Override
// 	public Bulletin1DocsDTO updateBulletin1(Long id, Bulletin1DocsDTO bulletin1DTO) {
// 		Bulletin1Docs existingBulletin1 = bulletin1Repository.findById(id)
//         .orElseThrow(() -> new UserNotFoundException("Bulletin1 Docs not found with ID: " + id));;
//         Bulletin1Docs updatedBulletin1 = Bulletin1DocsMapper.toEntity(bulletin1DTO);
// 		updatedBulletin1.setId(existingBulletin1.getId());
// 		bulletin1Repository.save(updatedBulletin1);
// 		return Bulletin1DocsMapper.toDTO(updatedBulletin1);
// 	}
// 	@Override
// 	public Bulletin1DocsDTO getBulletin1ById(Long id) {
//         Bulletin1Docs entity = bulletin1Repository.findById(id)
//         .orElseThrow(() -> new UserNotFoundException("Bulletin1 Docs not found with ID: " + id));
//         Bulletin1DocsDTO dto = Bulletin1DocsMapper.toDTO(entity); 
//         if(entity.getLokSabhaSessionNo()!=null)
//         {
//             MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
//             dto.setLokSabhaSessionNoDetails(lokSabhaSession);  
//         }
//         if(entity.getLokSabhaNo()!=null)
//         {
//             MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
//             dto.setLokSabhaNoDetails(lokSabha);
//         }
//         if(entity.getBranchName()!=null)
//         {
//             MastersBranchDTO branch=getBranchDetails(entity.getBranchName());
//             dto.setBranchNameDetails(branch); 
//         }
//         if(entity.getApplicationNo()!=null)
//         {
//             ApplicationDTO application=getApplicationDetails(entity.getApplicationNo());
//             dto.setApplicationName(application);  
//         }
//         return dto;
// 	}
// 	@Override
// 	public List<Bulletin1DocsDTO> getAllBulletin1s() 
//     {   
//         List<Bulletin1DocsDTO> dtoList = new ArrayList<>();
//         for (Bulletin1Docs entity : bulletin1Repository.findAll()) 
//         {
//             Bulletin1DocsDTO dto = Bulletin1DocsMapper.toDTO(entity); 
//             if(entity.getLokSabhaSessionNo()!=null)
//             {
//                 MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
//                 dto.setLokSabhaSessionNoDetails(lokSabhaSession);  
//             }
//             if(entity.getLokSabhaNo()!=null)
//             {
//             MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
//             dto.setLokSabhaNoDetails(lokSabha);
//             }
//             if(entity.getBranchName()!=null)
//             {
//             MastersBranchDTO branch=getBranchDetails(entity.getBranchName());
//             dto.setBranchNameDetails(branch); 
//             }
//             if(entity.getApplicationNo()!=null)
//             {
//             ApplicationDTO application=getApplicationDetails(entity.getApplicationNo());
//             dto.setApplicationName(application);  
//             }
//             dtoList.add(dto);
//         }
//         return dtoList;
//     }
//     private ApplicationDTO getApplicationDetails(Long id) {
//         return applicationRepository.findById(id)
//             .map(ApplicationMapper::toDTO)  
//             .orElse(null); 
       
//     }  
//     private MastersBranchDTO getBranchDetails(Long id) {
//         return masterBranchRepository.findById(id)
//             .map(BranchMapper::toDTO)  // Convert to DTO
//             .orElse(null);  // Return null if not found
//     }
//     private MastersLokSabhaSessionDTO getLokSabhaSessionDetails(Long id) {
//         // return masterLSSRepository.findById(id)
//         return masterLSSRepository.findById(id)
//             .map(LokSabhaSessionMapper::toDTO)  // Convert to DTO
//             .orElse(null);  // Return null if not found
//     }
//     private MastersLokSabhaDTO getLokSabhaDetails(Long id) {
//         return masterLSRepository.findById(id)
//             .map(LokSabhaMapper::toDTO)  // Convert to DTO
//             .orElse(null);  // Return null if not found
//     }

// 	@Override
// 	public void deleteBulletin1ById(Long id) {
// 		if (!bulletin1Repository.existsById(id)) {
// 			throw new UserNotFoundException("Bulletin1 Docs not found with ID "+ id);
// 		}
// 		bulletin1Repository.deleteById(id);
// 	}
// 	@Override
//     public Long getSessionId(HttpSession session) {
// 		Long userId = (Long) session.getAttribute(USER_ID_SESSION_ATTR);
//         if (userId != null) {
//             LOGGER.info("Retrieved User ID {} from session.", userId);
//         } else {
//             LOGGER.warn("No User ID found in session.");
//         }
//         return userId;
//     }
// 	@Override
// 	public void setSessionId(Long userId,HttpSession session) {
//         if (userId != null) {
//             session.setAttribute(USER_ID_SESSION_ATTR, userId);
//             LOGGER.info("User ID {} set in session.", userId);
//         } else {
//             LOGGER.warn("Attempted to set a null user ID in session.");
//         }
//     }
//  @Override
//     public List<Bulletin1DocsDTO> getAllInactive() {
//         Specification<Bulletin1Docs> isActiveSpec = Bulletin1DocsSpec.isActive(false);
//         List<Bulletin1Docs> inactiveEntities = bulletin1Repository.findAll(isActiveSpec);
//         return inactiveEntities.stream()
// 		.map(Bulletin1DocsMapper::toDTO)
// 		.collect(Collectors.toList());
//     }
// 	@Override
//     public List<Bulletin1DocsDTO> getAllActive() {
//         Specification<Bulletin1Docs> isActiveSpec = Bulletin1DocsSpec.isActive(true);
//         List<Bulletin1Docs> inactiveEntities = bulletin1Repository.findAll(isActiveSpec);
//         return inactiveEntities.stream()
// 		.map(Bulletin1DocsMapper::toDTO)
// 		.collect(Collectors.toList());
//     }
//     private void validateRequiredFields(Bulletin1DocsDTO bulletin1DTO) {
//         if (bulletin1DTO.getLokSabhaNo() == null) {
//             throw new IllegalArgumentException("Lok Sabha No is required for finalization.");
//         }
//         if (bulletin1DTO.getLokSabhaSessionNo() == null) {
//             throw new IllegalArgumentException("Session No is required for finalization.");
//         }
//     }
    
//     public List<Bulletin1DocsDTO> getByLokSabhaSessionNo(Long lokSabhaSessionNo) 
//     {
//         List<Bulletin1DocsDTO> dtoList = new ArrayList<>();
//         for (Bulletin1Docs entity : bulletin1Repository.findByLokSabhaSessionNo(lokSabhaSessionNo)) 
//         {
//             Bulletin1DocsDTO dto = Bulletin1DocsMapper.toDTO(entity);         
//             if(entity.getLokSabhaSessionNo()!=null)
//             {
//                 MastersLokSabhaSessionDTO lokSabhaSession=getLokSabhaSessionDetails(entity.getLokSabhaSessionNo());
//                 dto.setLokSabhaSessionNoDetails(lokSabhaSession);  
//             }
//             if(entity.getLokSabhaNo()!=null)
//             {
//             MastersLokSabhaDTO lokSabha=getLokSabhaDetails(entity.getLokSabhaNo());
//             dto.setLokSabhaNoDetails(lokSabha);
//             }
//             if(entity.getBranchName()!=null)
//             {
//             MastersBranchDTO branch=getBranchDetails(entity.getBranchName());
//             dto.setBranchNameDetails(branch); 
//             }
//             if(entity.getApplicationNo()!=null)
//             {
//             ApplicationDTO application=getApplicationDetails(entity.getApplicationNo());
//             dto.setApplicationName(application);  
//             }
//             dtoList.add(dto);
//         }
//         return dtoList;
//     }
   
// } 