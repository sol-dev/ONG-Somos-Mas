package com.team32.ong.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
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
            .content(objectMapper.writeValueAsString(dto))
        ).andExpect(MockMvcResultMatchers.status().isCreated())
         .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    public void testNewMember_badRequest_emptyName() throws Exception{
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
        Long id = 1l;
        dto.setImage("newImage");
        MemberDTO updatedDto = dto;
        updatedDto.setMemberId(id);
        
        Mockito.when(memberService.updateById(dto, id)).thenReturn(updatedDto);
        
        url= url + "/update/"+id;
        
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put(url)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(dto)) 
            ).andExpect(
                MockMvcResultMatchers.status().isOk()
            ).andExpect(
                MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedDto))
            );
    }

    


}
