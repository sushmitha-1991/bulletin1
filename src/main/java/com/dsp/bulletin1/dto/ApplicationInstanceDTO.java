package com.dsp.bulletin1.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationInstanceDTO {
	private Long id;
 private Long applicationId; // Reference to Application ID
 private String applicationNo;
 private String currentStatus;
//  private String publishedDocumentUrl;
//  private Long refId;
//  private String processInstanceId;
}
