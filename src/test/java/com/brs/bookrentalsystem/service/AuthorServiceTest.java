package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.author.AuthorRequest;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
import com.brs.bookrentalsystem.error.exception.impl.NoSuchEntityFoundException;
import com.brs.bookrentalsystem.model.Author;
import com.brs.bookrentalsystem.repo.AuthorRepo;
import com.brs.bookrentalsystem.service.serviceImpl.AuthorServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {


    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private Author duplicateAuthor;
    private AuthorRequest authorRequest;
    private AuthorResponse authorResponse;
    private static List<Author> mockAuthorDb;
    private static Set<Author> authorSet;

    @BeforeAll
    static void mockDatabase() {
        Author author1 = new Author(1, "author1", "author1@gmail.com", "9898989898");
        Author author2 = new Author(2, "author2", "author2@gmail.com", "9898989897");
        Author author3 = new Author(3, "author3", "author3@gmail.com", "9898989896");

        mockAuthorDb = List.of(author1, author2, author3);

        authorSet = Set.of(author1, author2, author3);

    }

    @BeforeEach
    void setUp() {

        authorRequest = AuthorRequest.builder()
                .id(1)
                .email("testAuthor@gmail.com")
                .name("testAuthor")
                .mobileNumber("98129011211")
                .build();

        author = authorService.toAuthor(authorRequest);

        authorResponse = AuthorResponse.builder()
                .id(1)
                .email("testAuthor@gmail.com")
                .name("testAuthor")
                .mobileNumber("98129011211")
                .build();

    }


    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should register new user")
    void registerAuthor() {

        // pre-condition
        given(authorRepo.save(author)).willReturn(author);

        System.out.println("Author to be saved : " + author);

        // action or behaviour under test
        AuthorResponse returnedAuthorResponse = authorService.registerAuthor(authorRequest);

        System.out.println("Author saved : " + authorResponse);

        // verification
        Assertions.assertNotNull(authorResponse);

        Assertions.assertEquals(authorResponse, returnedAuthorResponse);

    }

    @Test
    @DisplayName("Should throw Exception when duplicate author entity passed to persist")
    void shouldThrow_exception_whenDuplicateAuthor() {

        duplicateAuthor = author;
        when(authorRepo.save(duplicateAuthor)).thenThrow(new DataIntegrityViolationException("message"));

        System.out.println("Duplicate Author: " + duplicateAuthor);
        assertThrows(DataIntegrityViolationException.class, () -> authorService.registerAuthor(authorRequest));

    }


    @Test
    @DisplayName("Should return Author when valid author id is provided")
    void shouldReturnAuthor_whenAuthorIsValid() {
        Integer authorId = 1;
        when(authorRepo.findById(authorId)).thenReturn(Optional.of(author));

        AuthorResponse returnedAuthor = authorService.findAuthorById(authorId);

        Assertions.assertEquals(returnedAuthor, authorResponse);
    }

    @Test
    @DisplayName("Should throw NoSuchEntityFoundException when valid author id is provided")
    void shouldThrow_exception_whenAuthorIdIsInvalid() {
        Integer authorId = 100;
        when(authorRepo.findById(authorId)).thenReturn(Optional.empty());

//        authorService.findAuthorById(authorId);

       Exception exception = assertThrows(NoSuchEntityFoundException.class, () -> authorService.findAuthorById(authorId));

        String message = exception.getMessage();

        Assertions.assertEquals("AUTHOR WITH ID : 100 NOT FOUND", message);
    }


    @Test
    @DisplayName("Should return set of associated authors when list of valid AuthorId passed")
    void getAuthorsAssociated() {
        List<Integer> authorIdList = List.of(1, 2, 3);

        Author tempAuthor;

        System.out.println("Expected Author set : " + authorSet);

        for (Integer authorId : authorIdList) {
            tempAuthor = switch (authorId) {
                case 1 -> mockAuthorDb.get(0);
                case 2 -> mockAuthorDb.get(1);
                case 3 -> mockAuthorDb.get(2);
                default -> new Author(null, "defaultAuthor", "defaultAuthor@gmail.com", "812091238");
            };
            when(authorRepo.findById(authorId)).thenReturn(Optional.of(tempAuthor));
        }

        System.out.println("Received Author set : " + authorService.getAuthorAssociated(authorIdList));

        Assertions.assertEquals(authorSet, authorService.getAuthorAssociated(authorIdList));
    }

    @Test
    @DisplayName("Should delete registered Author given authorId")
    void deleteAuthorById() {

        Integer authorId = 1;

        doNothing().when(authorRepo).deleteById(authorId);

        // behaviour to test
        Message returnedMsg = authorService.deleteAuthorById(authorId);

        System.out.println("Returned Message : " + returnedMsg);

        verify(authorRepo, times(1)).deleteById(authorId);

        Assertions.assertNotNull(returnedMsg);

        Assertions.assertEquals("SUCCESS", returnedMsg.getCode());


    }


    @Test
    @DisplayName("Should return every Registered Authors")
    void shouldReturn_everyRegisteredAuthors() {

        AuthorResponse authorResponse1 = AuthorResponse.builder()
                .id(1)
                .name("author1")
                .email("author1@gmail.com")
                .mobileNumber("9898989898")
                .build();

        AuthorResponse authorResponse2 = AuthorResponse.builder()
                .id(2)
                .name("author2")
                .email("author2@gmail.com")
                .mobileNumber("9898989897")
                .build();

        AuthorResponse authorResponse3 = AuthorResponse.builder()
                .id(3)
                .name("author3")
                .email("author3@gmail.com")
                .mobileNumber("9898989896")
                .build();



        List<AuthorResponse> returnedAuthorResponses = List.of(authorResponse1, authorResponse2, authorResponse3);
        when(authorRepo.findAll()).thenReturn(mockAuthorDb);

        Assertions.assertEquals(returnedAuthorResponses, authorService.getRegisteredAuthors());

    }



    @Test
    @DisplayName("Should return Author entity when valid AuthorId is passed")
    void shouldReturnAuthorEntity_whenAuthorIdIsValid(){

        Integer authorId = 2;
        when(authorRepo.findById(authorId)).thenReturn(Optional.of(mockAuthorDb.get(0)));

        Author returnedAuthorEntity = authorService.getAuthorEntityById(authorId);

        Assertions.assertEquals(returnedAuthorEntity, mockAuthorDb.get(0));

    }
}

