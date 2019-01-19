package com.syscom.rest.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString(exclude = {})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
public class MessageDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String title;

	private String content;

	private LocalDate beginDate;

	private LocalDate endDate;

}
