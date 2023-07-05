package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.author.AuthorRequest;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
import com.brs.bookrentalsystem.error.codes.ErrorCodes;
import com.brs.bookrentalsystem.error.exception.impl.NoSuchEntityFoundException;
import com.brs.bookrentalsystem.model.Author;
import com.brs.bookrentalsystem.repo.AuthorRepo;
import com.brs.bookrentalsystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;

    private static final String NOT_FOUND_MESSAGE = "AUTHOR WITH ID : %d NOT FOUND";

    @Override
    public AuthorResponse registerAuthor(AuthorRequest request) {

        Author author = toAuthor(request);
        author = authorRepo.save(author);
        return toAuthorResponse(author);
    }


    // handle exception
    @Override
    public AuthorResponse updateAuthor(AuthorRequest request) {

        Author author = toAuthor(request);

        author = authorRepo.save(author);

        return toAuthorResponse(author);

    }


    // handle exception
    @Override
    public AuthorResponse findAuthorById(Integer authorId) {
        Author author = authorRepo
                .findById(authorId)
                .orElseThrow(() -> generateNoElementFoundException(authorId));

        return toAuthorResponse(author);
    }

    // handle exception
    @Override
    public Author getAuthorEntityById(Integer authorId) {
        return authorRepo.findById(authorId)
                .orElseThrow(() -> generateNoElementFoundException(authorId));
    }

    @Override
    public AuthorResponse getAuthor(Integer authorId) {
        return null;
    }

    @Override
    public Set<Author> getAuthorAssociated(List<Integer> authorIdList) {
        return authorIdList.stream()
                .map(this::toAuthor)
                .collect(Collectors.toSet());

    }

    @Override
    public List<AuthorResponse> getRegisteredAuthors() {

        List<Author> authorList = authorRepo.findAll();

        List<AuthorResponse> authorResponseList = authorList.stream()
                .map(this::toAuthorResponse)
                .collect(Collectors.toList());
        return authorResponseList;
    }

    @Override
    public Message deleteAuthorById(Integer authorId) {
        authorRepo.deleteById(authorId);
        return new Message("SUCCESS", "Author deleted successfully");
    }

    // handle exception
    private Author toAuthor(Integer authorId) {
        return authorRepo.findById(authorId).get();
    }

    private Author toAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setId(request.getId());
        author.setName(request.getName());
        author.setEmail(request.getEmail());
        author.setMobileNumber(request.getMobileNumber());
        return author;
    }

    private AuthorRequest toAuthorRequest(AuthorResponse response) {
        return AuthorRequest.builder()
                .id(response.getId())
                .name(response.getName())
                .email(response.getEmail())
                .mobileNumber(response.getMobileNumber())
                .build();
    }

    private AuthorResponse toAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .email(author.getEmail())
                .name(author.getName())
                .mobileNumber(author.getMobileNumber())
                .build();
    }

    private NoSuchEntityFoundException generateNoElementFoundException(Integer authorId) {
        return new NoSuchEntityFoundException(String.format(NOT_FOUND_MESSAGE, authorId), ErrorCodes.NOT_FOUND);
    }
}
