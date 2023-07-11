package com.brs.bookrentalsystem.controller.thymeleaf;


import com.brs.bookrentalsystem.controller.rest.AuthorController;
import com.brs.bookrentalsystem.controller.thymeleaf.author.AuthorViewController;
import com.brs.bookrentalsystem.dto.author.AuthorRequest;
import com.brs.bookrentalsystem.service.AuthorService;
import com.brs.bookrentalsystem.service.serviceImpl.AuthorServiceImpl;
import org.aspectj.bridge.MessageUtil;
import org.hibernate.loader.internal.AliasConstantsHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(AuthorViewController.class)
class AuthorViewControllerTest {

    private static final String BASE_URL = "/brs/admin/author";
    private static final String REDIRECTION_URL = "/brs/admin/author/";

    private static final String AUTHOR_MAIN_VIEW = "/author/author-page";
    private static final String AUTHOR_REQUEST_DTO = "authorRequest";
    private static final String AUTHOR_LIST = "authorList";

    MockMvc mockMvc;

    @Mock
    AuthorService authorService;

    @BeforeEach
    void init(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthorViewController(authorService))
                .build();

    }

    // test cases
    @Test
    @DisplayName("Get Authors view page")
    void testDefaultGetMapping() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(model().attribute(AUTHOR_REQUEST_DTO, new AuthorRequest()))
                .andExpect(model().attributeExists(AUTHOR_LIST))
                .andExpect(view().name(AUTHOR_MAIN_VIEW))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Add new author")
    void shouldRegisterNewAuthor() throws Exception {
        mockMvc.perform(post(BASE_URL + "/save")
                        .param("name", "authorName")
                        .param("email", "author@email.com")
                        .param("mobileNumber", "1122334455")
                        .flashAttr(AUTHOR_REQUEST_DTO, new AuthorRequest())
                )
                .andExpect(redirectedUrl(REDIRECTION_URL))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @DisplayName(value = "Empty author name is rejected")
    void shouldFail_whenName_NotProvided() throws Exception {
        mockMvc.perform(post(BASE_URL + "/save")
                .param("name", "")
                .param("email", "author@email.com")
                .param("mobileNumber", "1122334455")
        )
                .andExpect(model().attributeHasErrors(AUTHOR_REQUEST_DTO))
                .andExpect(model().attributeErrorCount(AUTHOR_REQUEST_DTO, 2));
    }

    @Test
    @DisplayName(value = "Author name starting with")
    void shouldFail_whenName_startsWithNumber() throws Exception {
        mockMvc.perform(post(BASE_URL + "/save")
                        .param("name", "12Author")
                        .param("email", "author@email.com")
                        .param("mobileNumber", "1122334455")
                )
                .andExpect(model().attributeHasErrors(AUTHOR_REQUEST_DTO))
                .andExpect(model().attributeErrorCount(AUTHOR_REQUEST_DTO, 1))
                .andDo(print());

    }

    @Test
    @DisplayName(value = "Invalid Email format is rejected")
    void shouldFail_whenEmail_isInvalid() throws Exception {
        mockMvc.perform(post(BASE_URL + "/save")
                        .param("name", "authorName")
                        .param("email", "author@email.")
                        .param("mobileNumber", "1122334455")
                )
                .andExpect(model().attributeHasErrors(AUTHOR_REQUEST_DTO));
    }

    @Test
    @DisplayName(value = "Mobile number should be 10 digits otherwise is rejected")
    void shouldFail_whenMobileNumber_isNotValid() throws Exception {
        mockMvc.perform(post(BASE_URL + "/save")

                        .param("email", "author@email.com")
                        .param("mobileNumber", "")
                )
                .andExpect(model().attributeHasErrors(AUTHOR_REQUEST_DTO))
                .andExpect(model().attributeErrorCount(AUTHOR_REQUEST_DTO, 3))
                .andDo(print());
    }



    @Test
    @DisplayName("Edit Author based on id")
    void editAuthor_test() throws Exception {

        mockMvc.perform(put(BASE_URL + "/{id}/edit", 1)
                .param("name", "updatedAuthorName")
                .param("email", "updatedEmail@gmail.com")
                .param("mobileNumber", "9812091287")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECTION_URL));
    }



}
