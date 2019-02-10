package com.syscom.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.syscom.AbstractTest;
import com.syscom.beans.Message;
import com.syscom.dao.MessageDao;
import com.syscom.exceptions.BusinessException;

public class MessageServiceTest extends AbstractTest {

	private static final String TITLE = "TITLE";
	private static final String CONTENT = "CONTENT";
	private static final LocalDate BEGIN_DATE = LocalDate.now();
	private static final LocalDate END_DATE = LocalDate.now().plusDays(1);

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageDao messageDao;

	@Test
	public void whenCreateNullMessageThenThrowException() throws Exception {
		// GIVEN
		exceptionRule.expect(IllegalArgumentException.class);

		// WHEN
		messageService.create(null);

		// THEN
	}

	@Test
	public void whenCreateEmptyMessageThenThrowException() throws Exception {
		// GIVEN
		exceptionRule.expect(BusinessException.class);

		// WHEN
		messageService.create(new Message());

		// THEN
	}

	@Test
	public void creatMessage() throws Exception {
		// GIVEN
		Message message = Message.builder().title(TITLE).content(CONTENT).beginDate(BEGIN_DATE).endDate(END_DATE)
				.build();

		// WHEN
		messageService.create(message);

		// THEN
		assertThat(messageDao.count()).isEqualTo(1);
	}

	@Test
	public void findAllMessage() throws Exception {
		// GIVEN
		Message message = Message.builder().title(TITLE).content(CONTENT).beginDate(BEGIN_DATE).endDate(END_DATE)
				.build();
		messageDao.save(message);

		// WHEN
		List<Message> messages = messageService.findAll();

		// THEN
		assertThat(messages.size()).isEqualTo(1);
	}

	@Test
	public void updateMessage() throws Exception {
		// GIVEN
		Message message = Message.builder().title(TITLE).content(CONTENT).beginDate(BEGIN_DATE).endDate(END_DATE)
				.build();
		message = messageDao.save(message);
		Message newMessage = Message.builder().title("TITLE_2").content("CONTENT_2")
				.beginDate(LocalDate.now().plusDays(1)).endDate(LocalDate.now().plusDays(2)).build();

		// WHEN
		messageService.update(message.getId(), newMessage);

		// THEN
		Optional<Message> optionalResult = messageDao.findById(message.getId());
		assertThat(optionalResult.isPresent()).isTrue();
		Message messageResult = optionalResult.get();
		assertThat(messageResult.getId()).isEqualTo(message.getId());
		assertThat(messageResult.getTitle()).isNotEqualTo(TITLE);
		assertThat(messageResult.getContent()).isNotEqualTo(CONTENT);
		assertThat(messageResult.getBeginDate()).isNotEqualTo(BEGIN_DATE);
		assertThat(messageResult.getEndDate()).isNotEqualTo(END_DATE);
	}

	@Test
	public void deleteMessage() throws Exception {
		// GIVEN
		Message message = Message.builder().title(TITLE).content(CONTENT).beginDate(BEGIN_DATE).endDate(END_DATE)
				.build();
		message = messageDao.save(message);

		// WHEN
		messageService.delete(message.getId());

		// THEN
		assertThat(messageDao.count()).isEqualTo(0);
	}
}
