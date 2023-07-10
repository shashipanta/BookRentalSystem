package com.brs.bookrentalsystem.repo;


import com.brs.bookrentalsystem.config.BookRentalJpaConfig;
import com.brs.bookrentalsystem.model.Author;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepoTest {

    private Author testAuthor = new Author(null, "testAuthor", "testAuthor@gmail.com", "9812762121");

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_find_no_tutorials_if_repo_is_empty(){
        Iterable<Author> authors = authorRepo.findAll();
        assertThat(authors).isEmpty();
    }

    @Test
    @DisplayName("Should store new Author")
    public void should_store_author(){
        Author savedAuthor = authorRepo.save(testAuthor);

        assertThat(savedAuthor)
                .hasFieldOrPropertyWithValue("name", "testAuthor")
                .hasFieldOrPropertyWithValue("email", "testAuthor@gmail.com")
                .hasFieldOrPropertyWithValue("mobileNumber", "9812762121");
    }


    @Test
    public void should_find_all_authors(){

        Author author1 = new Author(null, "Author1", "author1@gmail.com", "0000000000");
        Author author2 = new Author(null, "Author2", "author2@gmail.com", "1111111111");
        Author author3 = new Author(null, "Author3", "author3@gmail.com", "2222222222");

        List<Author> expectedAuthors = List.of(author1, author2, author3);

        entityManager.persist(author1);
        entityManager.persist(author2);
        entityManager.persist(author3);

        Iterable<Author> authors = authorRepo.findAll();

        assertThat(authors)
                .hasSize(3)
                .containsAll(expectedAuthors);
    }

}
