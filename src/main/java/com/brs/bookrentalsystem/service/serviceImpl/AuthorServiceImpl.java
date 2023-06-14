package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.author.AuthorRequest;
import com.brs.bookrentalsystem.dto.author.AuthorResponse;
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

    @Override
    public AuthorResponse registerAuthor(AuthorRequest request) {

        Author author = toAuthor(request);
        author = authorRepo.save(author);
        return toAuthorResponse(author);
    }


    @Override
    public AuthorResponse updateAuthor(AuthorRequest request, Short authorId) {
        return null;
    }

    // handle exception
    @Override
    public AuthorResponse findAuthorById(Integer authorId) {
        Author author = authorRepo.findById(authorId).orElseThrow();
        return toAuthorResponse(author);
    }

    // handle exception
    @Override
    public Author getAuthorById(Integer authorId) {
        return authorRepo.findById(authorId).orElseThrow();
    }

    @Override
    public Set<Author> getAuthorAssociated(List<Integer> authorIdList) {
        return authorIdList.stream()
                .map(this::toAuthor)
                .collect(Collectors.toSet());

    }

    @Override
    public List<AuthorResponse> getRegisteredAuthors() {
        return null;
    }

    @Override
    public Message deleteAuthorById(Short authorId) {
        return null;
    }

    // handle exception
    private Author toAuthor(Integer authorId){
        return authorRepo.findById(authorId).orElseThrow();
    }

    private Author toAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setId(request.getId());
        author.setName(request.getName());
        author.setEmail(request.getEmail());
        author.setMobileNumber(request.getMobileNumber());
        return author;
    }

    private AuthorResponse toAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .email(author.getEmail())
                .name(author.getName())
                .mobileNumber(author.getMobileNumber())
                .build();
    }
}
