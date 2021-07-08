package com.team32.ong.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team32.ong.constant.ConstantExceptionMessage;
import com.team32.ong.controller.SlidesController;
import com.team32.ong.dto.OrganizationPublicDTO;
import com.team32.ong.dto.SlideDto;
import com.team32.ong.dto.SlideDtoRequest;
import com.team32.ong.repository.SlideRepository;
import com.team32.ong.security.JWTUtil;
import com.team32.ong.service.SlideService;

import com.team32.ong.service.impl.UserImplService;
import javassist.NotFoundException;
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
import java.util.TreeMap;

@WebMvcTest(SlidesController.class)
public class SlidesControllerTest {

    @Autowired
    private SlidesController slidesController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SlideService slideService;
    @MockBean
    private UserImplService userImplService;
    @MockBean
    private JWTUtil jwtUtil;
    @MockBean
    private SlideRepository slideRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


        OrganizationPublicDTO org = new OrganizationPublicDTO("Somos Mas",
                "image", 111222333, "domicilio", "facebook", "linkedin",
                "instagram");

        SlideDto slideDto = new SlideDto("imagen", "texto imagen", 1, org );
        TreeMap<String, TreeMap<Integer, String>> response = new TreeMap<>();
        TreeMap<Integer, String> image = new TreeMap<>();
        SlideDtoRequest slideDtoRequest = new SlideDtoRequest("texto slide", 1);


    @Test
    public void getSlideTest() throws Exception {
        Mockito.when(slideService.findById(1L)).thenReturn(slideDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/slides/slides/1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getSlidesNotFoundTest() throws Exception {
        Mockito.when(slideService.findById(20L)).thenThrow(NotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/slides/slides/20")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void getSlidesTest() throws Exception {
        image.put(1,"image 1");
        image.put(2,"image 2");
        response.put("Somos Mas", image);
        Mockito.when(slideService.imageAndOrderByOrganization()).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/slides/list")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }



    @Test
    public void saveSlidesTest() throws Throwable {
        Mockito.when(slideService.save(slideDtoRequest, 1L)).thenReturn(slideDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/slides/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(slideDtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test void updateSlideTest() throws Exception {
        Mockito.when(slideService.update(1L, slideDtoRequest)).thenReturn(slideDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/slides/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(slideDtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteSlideTest() throws Throwable {
        Mockito.doNothing().when(slideService).deleteSlide(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/slides/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteSlideNotFoundTest() throws Throwable {
        Mockito.doThrow(new NotFoundException(ConstantExceptionMessage.MSG_NOT_FOUND))
        .when(slideService).deleteSlide(20L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/slides/20"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
