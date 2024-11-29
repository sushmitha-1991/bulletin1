package com.dsp.bulletin1.bulletin2.mapper;
import org.mapstruct.Mapper;

import com.dsp.bulletin1.bulletin2.dto.Bulletin2InputDTO;
import com.dsp.bulletin1.bulletin2.entity.Bulletin2Input;
@Mapper(componentModel = "spring")
public interface Bulletin2InputMapper 
{
    // Convert Entity to DTO
    Bulletin2InputDTO toDTO(Bulletin2Input entity);
    //convert DTO to Entity
    Bulletin2Input toEntity(Bulletin2InputDTO dto);    
}
