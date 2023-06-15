package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.model.Author;
import com.brs.bookrentalsystem.model.Book;
import com.brs.bookrentalsystem.model.Category;
import com.brs.bookrentalsystem.repo.BookRepo;
import com.brs.bookrentalsystem.service.AuthorService;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.CategoryService;
import com.brs.bookrentalsystem.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final DateUtil dateUtil;


    @Override
    public BookResponse saveBook(BookRequest request) {
        Book book = toBook(request);
        book = bookRepo.save(book);
        return toBookResponse(book);
    }

    private BookResponse toBookResponse(Book book) {

        String publishedDate = dateUtil.dateToString(book.getPublishedDate());
        List<Author> authors = book.getAuthor().stream()
                .toList();

        return BookResponse.builder()
                .bookId(book.getId())
                .bookName(book.getName())
                .isbn(book.getIsbn())
                .photoPath(book.getPhoto())
                .rating(book.getRating())
                .stockCount(book.getStockCount())
                .totalPages(book.getTotalPages())
                .publishedDate(publishedDate)
                .category(book.getCategory())
                .authors(authors)
                .build();
    }

    @Override
    public List<BookResponse> getSavedBooks() {
        return null;
    }

    @Override
    public BookResponse getBookById(Integer bookId) {
        return null;
    }


    // handle exception
    @Override
    public Book getBookEntityById(Integer bookId) {
        return bookRepo.findById(bookId).orElseThrow();
    }

    @Override
    public BookResponse updateBook(BookRequest request, Integer bookId) {
        return null;
    }

    @Override
    public Message deleteBookById(Integer bookId) {
        return null;
    }


    public Book toBook(BookRequest request){

        Category category = categoryService.getCategoryById(request.getCategory());
        Set<Author> authors = authorService.getAuthorAssociated(request.getAuthors());
        LocalDate publishedDate = dateUtil.stringToDate(request.getPublishedDate());

        Book book = new Book();

        if(request.getBookId() != null) book.setId(request.getBookId());
        if(request.getBookName() != null) book.setName(request.getBookName());
        if(request.getTotalPages() != null) book.setTotalPages(request.getTotalPages());
        if(request.getIsbn() != null) book.setIsbn(request.getIsbn());
        if(request.getStockCount() != null) book.setStockCount(request.getStockCount());
        if(request.getPublishedDate() != null) book.setPublishedDate(publishedDate);
        if(request.getRating() != null) book.setRating(request.getRating());
        if(request.getCategory() != null) book.setCategory(category);
        if(request.getAuthors() != null) book.setAuthor(authors);
        if(request.getPhotoPath() != null) book.setPhoto(request.getPhotoPath());

        return book;
    }
}
