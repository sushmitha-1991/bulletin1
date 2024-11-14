
    package com.dsp.bulletin1.dto;


    import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public class MasterCodeDTO {
        @NotEmpty
        private Long id;
        @NotEmpty
        private String abbr;
        // @NotEmpty
        
        // @NotEmpty
        // private Long codeTypeId;
    
        // @NotEmpty
        // private String code;
    
       
    
        // private String description;    
        // @NotEmpty
        // private Long createdBy;
        }
    
        
        
    
    
    