package com.syscom.rest.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.syscom.beans.Fonction;
import com.syscom.rest.dto.FonctionDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {})
public interface FonctionMapper {


	FonctionDTO beanToDto(Fonction fonction);

	@InheritInverseConfiguration
	@Mappings({ 
		@Mapping(target = "id", ignore = true), 
		@Mapping(target = "createDate", ignore = true),
		@Mapping(target = "updateDate", ignore = true),
		@Mapping(target = "roles", ignore = true)
	})
	Fonction dtoToBean(FonctionDTO fonctionDTO);
	
	
	
}
