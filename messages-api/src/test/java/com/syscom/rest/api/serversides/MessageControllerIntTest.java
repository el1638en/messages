package com.syscom.rest.api.serversides;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.syscom.beans.Fonction;
import com.syscom.beans.Message;
import com.syscom.beans.Role;
import com.syscom.beans.User;
import com.syscom.dao.FonctionDAO;
import com.syscom.dao.RoleDao;
import com.syscom.rest.TestUtil;
import com.syscom.rest.api.MessageController;
import com.syscom.rest.dto.MessageDTO;
import com.syscom.service.MessageService;
import com.syscom.service.UserService;

public class MessageControllerIntTest extends AbstractIntTest {
	
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
    public void setup() {
		Role role = Role.builder().code("ADMIN").libelle("ADMIN").build();
		role = roleDao.save(role);
		List<Role> roles = Arrays.asList(role);
		
		fonctionDao.save(Fonction.builder().code("AJOUTER_MESSAGE").libelle("AJOUTER_MESSAGE").roles(roles).build());
		fonctionDao.save(Fonction.builder().code("CONSULTER_MESSAGE").libelle("CONSULTER_MESSAGE").roles(roles).build());
		fonctionDao.save(Fonction.builder().code("MODIFIER_MESSAGE").libelle("MODIFIER_MESSAGE").roles(roles).build());
		fonctionDao.save(Fonction.builder().code("SUPPRIMER_MESSAGE").libelle("SUPPRIMER_MESSAGE").roles(roles).build());
		
		user = User.builder().login(LOGIN).password(PASSWORD).name(NAME).firstName(FIRST_NAME).role(role).build();
		
    }
    
	@Test
	public void testCreateMessageWithoutValidToken() throws Exception {
		// GIVEN
		MessageDTO messageDTO = MessageDTO.builder().title(TITLE).content(CONTENT).beginDate(BEGIN_DATE).endDate(END_DATE).build();
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.post(MessageController.PATH).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
         .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testCreateMessageWithWrongToken() throws Exception {
		// GIVEN
		MessageDTO messageDTO = MessageDTO.builder().title(TITLE).content(CONTENT).beginDate(BEGIN_DATE).endDate(END_DATE).build();
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.post(MessageController.PATH).header(HttpHeaders.AUTHORIZATION, "Bearer wrongTokendcqscsqcqsvsdvsdfv").contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
         .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testCreateWrongMessage() throws Exception {
		// GIVEN
		userService.create(user);
		MessageDTO messageDTO = new MessageDTO();
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.post(MessageController.PATH).header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(LOGIN, PASSWORD)).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
         .andExpect(status().isBadRequest());
	}
	
	
	
	@Test
	public void testCreateMessage() throws Exception {
		// GIVEN
		userService.create(user);
		MessageDTO messageDTO = MessageDTO.builder().title(TITLE).content(CONTENT).beginDate(BEGIN_DATE).endDate(END_DATE).build();
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.post(MessageController.PATH).header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(LOGIN, PASSWORD)).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
         .andExpect(status().isOk());
	}
	
	
	@Test
	public void testFindAllMessages() throws Exception {
		// GIVEN
		userService.create(user);
		messageService.create(new Message(null, TITLE, CONTENT, BEGIN_DATE, END_DATE));
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.get(MessageController.PATH).header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(LOGIN, PASSWORD)))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.length()").value(1))
         .andExpect(jsonPath("$.[0].title").value(TITLE))
		 .andExpect(jsonPath("$.[0].content").value(CONTENT));
	}

	@Test
	public void testFindMessageById() throws Exception {
		// GIVEN
		userService.create(user);
		Message message = messageService.create(new Message(null, TITLE, CONTENT, BEGIN_DATE, END_DATE));
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.get(MessageController.PATH+"/"+message.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(LOGIN, PASSWORD)))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.title").value(TITLE))
		 .andExpect(jsonPath("$.content").value(CONTENT));
	}
	
	@Test
	public void testUpdateMessage() throws Exception {
		// GIVEN
		userService.create(user);
		Message message = messageService.create(new Message(null, TITLE, CONTENT, BEGIN_DATE, END_DATE));
		
		MessageDTO messageDTO = MessageDTO.builder().title("NEW_TITLE").content("NEW_CONTENT").beginDate(BEGIN_DATE).endDate(END_DATE).build();
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.put(MessageController.PATH+"/"+message.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(LOGIN, PASSWORD)).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.title").value("NEW_TITLE"))
		 .andExpect(jsonPath("$.content").value("NEW_CONTENT"));
	}
	
	
	@Test
	public void testDeleteMessage() throws Exception {
		// GIVEN
		userService.create(user);
		Message message = messageService.create(new Message(null, TITLE, CONTENT, BEGIN_DATE, END_DATE));
		
		// WHEN
		
		// THEN		
		 mockMvc
         .perform(MockMvcRequestBuilders.delete(MessageController.PATH+"/"+message.getId()).header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(LOGIN, PASSWORD)).contentType(TestUtil.APPLICATION_JSON_UTF8))
         .andExpect(status().isOk());
		 
		 assertThat(messageService.findAll().size()).isEqualTo(0);
	}
}
