package com.team32.ong.controllersTest;

import com.team32.ong.controller.SlidesController;
import com.team32.ong.model.OrganizationEntity;
import com.team32.ong.model.Slide;
import com.team32.ong.service.SlideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Date;

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


    @Test
    public void getSlideTest(){

        OrganizationEntity org = new OrganizationEntity(1l,
                                                     "Somos Mas",
                                                     "image",
                                                     "domicilio",
                                                     11122233,
                                                     "organizacion@somos-mas.com",
                                                     "bienvenidos",
                                                     "about",
                                                     null,
                                                     "facebook-somos-mas",
                                                     "linkedin-somos-mas",
                                                     "instagram-somos-mas",
                                                     LocalDateTime.now(),
                                                     LocalDateTime.now(),
                                                    false);
        Slide slide = new Slide(1l,
                                "image",
                                "Slide 1",
                                1,
                                 org,
                                 LocalDateTime.now(),
                                 new Date(),
                                false);


    }
}
