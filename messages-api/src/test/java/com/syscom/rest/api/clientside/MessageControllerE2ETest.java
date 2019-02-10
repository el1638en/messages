package com.syscom.rest.api.clientside;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.syscom.beans.Fonction;
import com.syscom.beans.Message;
import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.dao.FonctionDAO;
import com.syscom.dao.RoleDao;
import com.syscom.rest.api.MessageController;
import com.syscom.rest.dto.MessageDTO;
import com.syscom.service.MessageService;
import com.syscom.service.UserService;

public class MessageControllerE2ETest extends AbstractE2ETest {

	private static final String TITLE = "TITLE";
	private static final String CONTENT = "CONTENT";
	private static final LocalDate BEGIN_DATE = LocalDate.now();
	private static final LocalDate END_DATE = LocalDate.now().plusDays(1);
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private FonctionDAO fonctionDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	private User user;
		

    @Before
    public void setup() throws Exception {
		Role role = Role.builder().code("ADMIN").libelle("ADMIN").build();
		role = roleDao.save(role);
		List<Role> roles = Arrays.asList(role);
		
		fonctionDao.save(Fonction.builder().code("AJOUTER_MESSAGE").libelle("AJOUTER_MESSAGE").roles(roles).build());
		fonctionDao.save(Fonction.builder().code("CONSULTER_MESSAGE").libelle("CONSULTER_MESSAGE").roles(roles).build());
		fonctionDao.save(Fonction.builder().code("MODIFIER_MESSAGE").libelle("MODIFIER_MESSAGE").roles(roles).build());
		fonctionDao.save(Fonction.builder().code("SUPPRIMER_MESSAGE").libelle("SUPPRIMER_MESSAGE").roles(roles).build());
		
		user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(role).build();
		userService.create(user);
    }
    
    
    
	@Test
	public void testCreateMessage() throws Exception {
		// GIVEN
		MessageDTO messageDTO = MessageDTO.builder().title(TITLE).content(CONTENT).beginDate(BEGIN_DATE).endDate(END_DATE).build();
		
        HttpEntity<MessageDTO> request = new HttpEntity<>(messageDTO, getSecuredHeaders());
        
		// WHEN
		ResponseEntity<String> response = testRestTemplate.postForEntity(MessageController.PATH, request, String.class);
		
		
		// THEN		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(messageService.findAll().size()).isEqualTo(1);
	}
	
	
	@Test
	public void testFindAllMessages() throws Exception {
		// GIVEN
		messageService.create(new Message(null, TITLE, CONTENT, BEGIN_DATE, END_DATE));
		
		// WHEN
		ResponseEntity<List<MessageDTO>> response =
				testRestTemplate.exchange(MessageController.PATH,
		                    HttpMethod.GET, new HttpEntity<>(getSecuredHeaders()), new ParameterizedTypeReference<List<MessageDTO>>(){});
		
		List<MessageDTO> messageDtos = response.getBody();
				
		
		// THEN		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(messageDtos).isNotEmpty();
		assertThat(messageDtos.size()).isEqualTo(1);
	}

	@Test
	public void testFindMessageById() throws Exception {
		// GIVEN
		Message message = messageService.create(new Message(null, TITLE, CONTENT, BEGIN_DATE, END_DATE));
		
		// WHEN
		ResponseEntity<MessageDTO> response = testRestTemplate.exchange(MessageController.PATH+"/"+message.getId(), 
				HttpMethod.GET, new HttpEntity<>(getSecuredHeaders()), MessageDTO.class);
		
		// THEN		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		MessageDTO messageDto = response.getBody();
		
		assertThat(messageDto).isNotNull();
		assertThat(messageDto.getTitle()).isEqualTo(message.getTitle());
		assertThat(messageDto.getContent()).isEqualTo(message.getContent());
		assertThat(messageDto.getBeginDate()).isEqualTo(message.getBeginDate());
		assertThat(messageDto.getEndDate()).isEqualTo(message.getEndDate());
	}
	
	@Test
	public void testUpdateMessage() throws Exception {
		// GIVEN
		Message message = messageService.create(new Message(null, TITLE, CONTENT, BEGIN_DATE, END_DATE));
		MessageDTO messageDTO = MessageDTO.builder().title("NEW_TITLE").content("NEW_CONTENT").beginDate(BEGIN_DATE).endDate(END_DATE).build();
        HttpEntity<MessageDTO> request = new HttpEntity<>(messageDTO, getSecuredHeaders());
        
		// WHEN
        ResponseEntity<MessageDTO> response = testRestTemplate.getRestTemplate().exchange(MessageController.PATH+"/"+message.getId(), HttpMethod.PUT, request, MessageDTO.class);
	
		
		// THEN
        MessageDTO result = response.getBody();
		assertThat(result).isNotNull();
		assertThat(result.getTitle()).isEqualTo("NEW_TITLE");
		assertThat(result.getContent()).isEqualTo("NEW_CONTENT");
	}
	
	
	@Test
	public void testDeleteMessage() throws Exception {
		// GIVEN
		Message message = messageService.create(new Message(null, TITLE, CONTENT, BEGIN_DATE, END_DATE));
		
		// WHEN
		testRestTemplate.getRestTemplate().exchange(MessageController.PATH+"/"+message.getId(), HttpMethod.DELETE, new HttpEntity<>(getSecuredHeaders()), MessageDTO.class);
		
		// THEN		 
		 assertThat(messageService.findAll().size()).isEqualTo(0);
	}
	
	private HttpHeaders getSecuredHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(user.getLogin(), user.getPassword()));
		return headers;
	}
}
