package com.dsp.bulletin1.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Builder
public class Bulletin1DocsDTO 
{
    private Long id;
    @NotEmpty
    private LocalDate refDocDate;
    @NotEmpty
    private Long refDocId;
    @NotEmpty
    private Long bulletin1Id;
    // private MasterCodeDTO codes;
    private BulletinDTO bulletin1Details;
	}

    
    


