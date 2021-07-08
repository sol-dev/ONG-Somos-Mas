package com.team32.ong.controllersTest;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.controller.ContactController;
import com.team32.ong.dto.ContactDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.security.JWTUtil;
import com.team32.ong.service.ContactService;
import com.team32.ong.service.impl.UserImplService;

@WebMvcTest(controllers = ContactController.class)
public class ContactControllerTest {

	@MockBean
	private ContactService contactService;
	
	@MockBean
	private UserImplService userService;
	
	@MockBean
	private JWTUtil jWTUtil;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	private void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getAll_returnAListContact() throws Exception{	
		ContactDTO contactExample = new ContactDTO("Lucas","lucas@gmail.com","mensagge",1233L);
		ContactDTO contactExample2 = new ContactDTO("Cristian","cristian@gmail.com","mensagge",1233L);
		ContactDTO contactExample3 = new ContactDTO("Guille","guille@gmail.com","mensagge",1233L);
		
		Mockito.when(contactService.getAll()).thenReturn(Arrays.asList(contactExample,contactExample2,contactExample3));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contact/backoffice/contacts"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)));
	}
	
	@Test
	public void createAContact_withCorrectAttributes_returnAContact() throws Exception{	
		ContactDTO contactDTOExample = new ContactDTO("Lucas","lucas@gmail.com","mensagge",1233L);
		
		Mockito.when(contactService.save(contactDTOExample)).thenReturn(contactDTOExample);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/contact")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(contactDTOExample)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(contactDTOExample)));
	}
	
	@Test
	public void createAContact_withIncorrectAttributes_returnBadRequestException() throws Exception{	
		ContactDTO contactDTO = new ContactDTO("","lucas@gmail.com","mensagge",1233L);
		
		Mockito.when(contactService.save(contactDTO)).thenThrow(new BadRequestException(ConstantExceptionMessage.MSG_NAME_BAD_REQUEST));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/contact")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(contactDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}