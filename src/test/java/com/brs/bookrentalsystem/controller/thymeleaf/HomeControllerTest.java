package com.brs.bookrentalsystem.controller.thymeleaf;

import com.brs.bookrentalsystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    private static final String BASE_URL = "/brs/";

    MockMvc mockMvc;

    @Mock
    BookService bookService;

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new HomeController(bookService)).build();
    }

    @Test
    public void testSimpleGet() throws Exception {
        // static import of MockMvcRequestBuilders.*
        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk());
    }

}
