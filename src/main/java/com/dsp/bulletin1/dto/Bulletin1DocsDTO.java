package com.dsp.bulletin1.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.dsp.bulletin1.entity.Bulletin1Details;
import com.dsp.bulletin1.entity.MasterCode;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long bulletinId;
    private MasterCodeDTO codes;
    private Bulletin1DetailsDTO bulletin1Details;
	}

    
    


