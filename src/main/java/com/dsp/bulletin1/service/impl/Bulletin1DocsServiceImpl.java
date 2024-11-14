package com.dsp.bulletin1.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dsp.bulletin1.dto.ApproverDTO;
import com.dsp.bulletin1.dto.Bulletin1DetailsDTO;
import com.dsp.bulletin1.dto.Bulletin1DocsDTO;
import com.dsp.bulletin1.dto.BulletinDTO;
import com.dsp.bulletin1.dto.MasterCodeDTO;
import com.dsp.bulletin1.entity.Bulletin1Details;
import com.dsp.bulletin1.entity.Bulletin1Docs;
import com.dsp.bulletin1.exception.UserNotFoundException;
import com.dsp.bulletin1.mapper.ApproverMapper;
import com.dsp.bulletin1.mapper.Bulletin1DetailsMapper;
import com.dsp.bulletin1.mapper.Bulletin1DocsMapper;
import com.dsp.bulletin1.mapper.CodeMapper;
import com.dsp.bulletin1.mapper.GetBulletinMapper;
import com.dsp.bulletin1.repository.Bulletin1DetailsRepository;
import com.dsp.bulletin1.repository.Bulletin1DocsRepository;
import com.dsp.bulletin1.repository.MasterCodeRepository;
import com.dsp.bulletin1.repository.MyUserRepository;
import com.dsp.bulletin1.service.Bulletin1DocsService;
@Service
public class Bulletin1DocsServiceImpl implements Bulletin1DocsService {

	private final Bulletin1DocsRepository bulletin1Repository;
    private final MasterCodeRepository masterRepository;
    private final MyUserRepository userRepository;
    private final Bulletin1DetailsRepository bulletin1DetailsRepository;
    // @Autowired
    // private RestTemplate restTemplate;
	@Autowired
	public Bulletin1DocsServiceImpl(Bulletin1DocsRepository bulletin1Repository,Bulletin1DetailsRepository bulletin1DetailsRepository,
      MyUserRepository userRepository, MasterCodeRepository masterRepository)
    {	
        this.userRepository=userRepository;
        this.masterRepository=masterRepository;
        this.bulletin1Repository = bulletin1Repository;
        this.bulletin1DetailsRepository=bulletin1DetailsRepository;

	}

	@Override
	public Bulletin1DocsDTO createBulletin1(Bulletin1DocsDTO bulletin1DTO) {
        System.out.println("dddddddddddd");
        System.out.println(bulletin1DTO);
		Bulletin1Docs Bulletin1Docs = Bulletin1DocsMapper.toEntity(bulletin1DTO);
        
		Bulletin1Docs = bulletin1Repository.save(Bulletin1Docs);
		return Bulletin1DocsMapper.toDTO(Bulletin1Docs);
	}
	@Override
	public Bulletin1DocsDTO updateBulletin1(Long id, Bulletin1DocsDTO bulletin1DTO) {
		Bulletin1Docs existingBulletin1 = bulletin1Repository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Bulletin1 Docs not found with ID: " + id));
        
		Bulletin1Docs updatedBulletin1= Bulletin1DocsMapper.toEntity(bulletin1DTO);
		updatedBulletin1.setId(existingBulletin1.getId());
		bulletin1Repository.save(updatedBulletin1);
		return Bulletin1DocsMapper.toDTO(updatedBulletin1);
	}
	@Override
	public Bulletin1DocsDTO getBulletin1ById(Long id) {
        Bulletin1Docs entity = bulletin1Repository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Bulletin1 Docs  not found with ID: " + id));
        Bulletin1DocsDTO dto = Bulletin1DocsMapper.toDTO(entity); 
        if(entity.getBulletin1Id()!=null)
            {
                BulletinDTO bulletin=getBulletin1Details(entity.getBulletin1Id());
                dto.setBulletin1Details(bulletin); 
            }
        if(entity.getRefDocId()!=null)
            {
                MasterCodeDTO codes=getCodeDetails(entity.getRefDocId());
                dto.setCodes(codes); 
            }      
        return dto;
    }

    @Override
	public List<Bulletin1DocsDTO> getAllBulletin1s() 
    {   
        List<Bulletin1DocsDTO> dtoList = new ArrayList<>();
        for (Bulletin1Docs entity : bulletin1Repository.findAll()) 
        {

            Bulletin1DocsDTO dto = Bulletin1DocsMapper.toDTO(entity); 

            if(entity.getBulletin1Id()!=null)
            {
                BulletinDTO bulletin=getBulletin1Details(entity.getBulletin1Id());
                dto.setBulletin1Details(bulletin); 
            }  
            if(entity.getRefDocId()!=null)
            {
                MasterCodeDTO codes=getCodeDetails(entity.getRefDocId());
                dto.setCodes(codes); 
            }
            dtoList.add(dto);    
        }
        return dtoList;
	}
    private MasterCodeDTO getCodeDetails(Long id) {
        return masterRepository.findById(id)
            .map(CodeMapper::toDTO)  // Convert to DTO
            .orElse(null);  // Return null if not found
    }
    public BulletinDTO getBulletin1Details(Long id) {

        Bulletin1Details entity = bulletin1DetailsRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Bulletin1 Docs  not found with ID: " + id));
        BulletinDTO dto = GetBulletinMapper.toDTO(entity);
        return dto;
    
    }
}


