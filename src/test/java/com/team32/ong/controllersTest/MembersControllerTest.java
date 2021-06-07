package com.team32.ong.controllerTests;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.controller.MemberController;
import com.team32.ong.dto.MemberDTO;
import com.team32.ong.exception.custom.BadRequestException;
import com.team32.ong.repository.MemberRepository;
import com.team32.ong.security.JWTUtil;
import com.team32.ong.service.IMemberService;
import com.team32.ong.service.impl.UserImplService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javassist.NotFoundException;

@WebMvcTest(MemberController.class)
public class MembersControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserImplService userImplService;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private IMemberService memberService;

    @MockBean
    private MemberRepository memberRepository;
    
    private MemberDTO dto;

    String url = "/api/v1/members";
    
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        dto = new MemberDTO();
        dto.setName("María Irola");
        dto.setFacebookUrl("facebookUrl");
        dto.setInstagramUrl("instagramUrl");
        dto.setLinkedinUrl("linkedinUrl");
        dto.setImage("image");
        dto.setDescription("Presidenta");
    }
    
    
    
    @Test
    public void testNewMember_created() throws Exception{

        Mockito.when(memberService.save(dto)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    public void testNewMember_badRequest() throws Exception{
        dto.setName("");
        Mockito.when(memberService.save(dto)).thenThrow(BadRequestException.class);

        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(dto))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest() );
    }

    @Test
    public void testUpdate_ok() throws Exception{
        dto.setImage("newImage");
        MemberDTO updatedDto = new MemberDTO(1l,"María Irola" , "facebookUrl", "instagramUrl", "linkedinUrl", "newImage", "Presidenta");
        Long id = 1l;

        Mockito.when(memberService.updateById(dto, id)).thenReturn(updatedDto);
        
        url= url + "/update/"+id;
        
        mockMvc.perform(MockMvcRequestBuilders
            .put(url)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(dto)) )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedDto)));
    }

    @Test
    public void testUpdate_notFound() throws Exception{
        Long id = 100l;
        dto.setImage("newImage");
        
        Mockito.when(memberService.updateById(dto, id)).thenThrow(NotFoundException.class);
        
        url= url + "/update/"+id;
        
        mockMvc.perform(MockMvcRequestBuilders
            .put(url)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(dto))  )
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    public void testUpdate_badRequest() throws Exception{
        Long id = 100l;
        dto.setImage("");
        
        Mockito.when(memberService.updateById(dto, id)).thenThrow(BadRequestException.class);
        
        url= url + "/update/"+id;
        
        mockMvc.perform(MockMvcRequestBuilders
            .put(url)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetMembers_ok() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetMembers_notFound() throws Exception{
        url = url+ "?page=5";
        Pageable pageable = PageRequest.of(5, 3);

        Mockito.when(memberService.getMembers(pageable)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testSoftDelete_ok()throws Exception{
        Long id = 1l;
        
        Mockito.doNothing().when(memberService).softDelete(id);
        url= url + "/"+id;
        
        mockMvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSoftDelete_notFound()throws Exception{
        Long id = 100l;
        
        Mockito.doThrow(new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND+id)).when(memberService).softDelete(id);
        url= url + "/"+id;
        
        mockMvc.perform(MockMvcRequestBuilders.delete(url)).andExpect( MockMvcResultMatchers.status().isNotFound());
    }


}
