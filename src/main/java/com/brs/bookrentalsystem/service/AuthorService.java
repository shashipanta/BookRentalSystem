package com.brs.bookrentalsystem.service;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.author.AuthorRequest;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
import com.brs.bookrentalsystem.model.Author;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    AuthorResponse registerAuthor(AuthorRequest request);

    AuthorResponse updateAuthor(AuthorRequest request, Short authorId);

    AuthorResponse findAuthorById(Integer authorId);

    Author getAuthorById(Integer authorId);

    Set<Author> getAuthorAssociated(List<Integer> authorIdList);

    List<AuthorResponse> getRegisteredAuthors();

    Message deleteAuthorById(Short authorId);
}
