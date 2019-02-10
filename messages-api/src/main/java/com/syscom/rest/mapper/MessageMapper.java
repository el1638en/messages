package com.syscom.rest.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.syscom.beans.Message;
import com.syscom.rest.dto.MessageDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {})
public interface MessageMapper {

	MessageDTO beanToDto(Message message);

	@InheritInverseConfiguration
	@Mappings({ 
		@Mapping(target = "createDate", ignore = true),
		@Mapping(target = "updateDate", ignore = true)
	})
	Message dtoToBean(MessageDTO messageDTO);
	
	List<MessageDTO> beansToDtos(List<Message> messages);
	
}
