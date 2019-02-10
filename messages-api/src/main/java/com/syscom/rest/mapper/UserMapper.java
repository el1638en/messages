package com.syscom.rest.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.syscom.beans.User;
import com.syscom.rest.dto.UserDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {FonctionMapper.class})
public interface UserMapper {
	
	@Mappings({ 
		@Mapping(target = "role", source = "role.code"),
		@Mapping(target = "fonctions", source = "role.fonctions")
	})
	UserDTO beanToDto(User user);

	@InheritInverseConfiguration
	@Mappings({ 
		@Mapping(target = "id", ignore = true), 
		@Mapping(target = "createDate", ignore = true),
		@Mapping(target = "updateDate", ignore = true)
	})
	User dtoToBean(UserDTO userDTO);

}
