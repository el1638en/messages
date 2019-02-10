package com.syscom.rest.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import com.syscom.beans.Token;
import com.syscom.rest.dto.TokenDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {UserMapper.class})
public interface TokenMapper {

	@Mappings({ 
		@Mapping(target = "userDTO", source = "user")
	})
	TokenDTO beanToDto(Token token);

	@InheritInverseConfiguration
	@Mappings({ 
		@Mapping(target = "id", ignore = true),
		@Mapping(target = "createDate", ignore = true),
		@Mapping(target = "updateDate", ignore = true)
	})
	Token dtoToBean(TokenDTO tokenDTO);

}
