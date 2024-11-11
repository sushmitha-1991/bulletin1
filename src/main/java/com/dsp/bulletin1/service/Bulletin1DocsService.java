// package com.dsp.bulletin1.service;


// 	import java.util.List;

// import org.springframework.stereotype.Service;

// import com.dsp.bulletin1.dto.Bulletin1DocsDTO;

// import jakarta.servlet.http.HttpSession;


	    
// 	    @Service
// 	    public interface Bulletin1DocsService {
	    
// 	        Bulletin1DocsDTO createBulletin1(Bulletin1DocsDTO bulletin1DTO);

// 	        Bulletin1DocsDTO updateBulletin1(Long id, Bulletin1DocsDTO bulletin1DTO);
	    
// 	        Bulletin1DocsDTO getBulletin1ById(Long id);
	    
// 	        List<Bulletin1DocsDTO> getAllBulletin1s();
	    
// 	        void deleteBulletin1ById(Long id);

// 	        Long getSessionId(HttpSession session);

// 			void setSessionId(Long id,HttpSession session);

// 			List<Bulletin1DocsDTO> getAllInactive();

// 			List<Bulletin1DocsDTO> getAllActive();

// 			List<Bulletin1DocsDTO> getByLokSabhaSessionNo(Long lokSabhaNo);			

// 	    }