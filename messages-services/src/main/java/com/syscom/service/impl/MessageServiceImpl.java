package com.syscom.service.impl;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.syscom.beans.Message;
import com.syscom.dao.MessageDao;
import com.syscom.exceptions.BusinessException;
import com.syscom.service.BaseService;
import com.syscom.service.MessageService;

/**
 * Implementation du contrat d'interface des services métiers des messages
 *
 */
@Service
@Transactional
public class MessageServiceImpl extends BaseService implements MessageService {

	@Autowired
	private MessageDao messageDao;

	@Override
	public Message create(Message message) throws BusinessException {
		Assert.notNull(message, getMessage("message.not.null"));
		List<String> errors = this.verifierMessage(message);
		if (CollectionUtils.isNotEmpty(errors)) {
			throw new BusinessException(StringUtils.join(errors, " "));
		}
		return messageDao.save(message);
	}

	@Override
	public Message findById(Long id) {
		Assert.notNull(id, getMessage("message.id.not.null"));
		Optional<Message> optionalMessage = messageDao.findById(id);
		if (optionalMessage.isPresent()) {
			return optionalMessage.get();
		} else {
			return null;
		}
	}

	@Override
	public List<Message> findAll() {
		return StreamSupport.stream(messageDao.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Message update(Long id, Message message) throws BusinessException {
		Assert.notNull(id, getMessage("message.id.not.null"));
		Assert.notNull(message, getMessage("message.not.null"));
		Message findMessage = findById(id);
		if (findMessage == null) {
			throw new BusinessException(getMessage("message.unknown.error"));
		}

		findMessage.setTitle(message.getTitle());
		findMessage.setContent(message.getContent());
		findMessage.setBeginDate(message.getBeginDate());
		findMessage.setEndDate(message.getEndDate());
		return messageDao.save(findMessage);
	}

	@Override
	public void delete(Long id) throws BusinessException {
		Assert.notNull(id, getMessage("message.id.not.null"));
		if (!messageDao.existsById(id)) {
			throw new BusinessException(getMessage("message.unknown.error"));
		}
		messageDao.deleteById(id);

	}

	/**
	 * V�rifie si les donn�es du messages sont pr�sentes.
	 * 
	 * @param messageDTO objet DTO du message {@link MessageDTO}
	 * @return liste de messages d'erreurs �ventuels.
	 */
	private List<String> verifierMessage(Message message) {
		List<String> errors = new ArrayList<>();
		if (isEmpty(message.getTitle())) {
			errors.add(getMessage("message.title.not.null"));
		}
		if (isEmpty(message.getContent())) {
			errors.add(getMessage("message.content.not.null"));
		}
		if (message.getBeginDate() == null) {
			errors.add(getMessage("message.begin.date.not.null"));
		}
		if (message.getEndDate() == null) {
			errors.add(getMessage("message.end.date.not.null"));
		}
		return errors;
	}

}
