package com.brs.bookrentalsystem.service.serviceImpl;

import com.brs.bookrentalsystem.dto.Message;
import com.brs.bookrentalsystem.dto.book.BookRequest;
import com.brs.bookrentalsystem.dto.book.BookResponse;
import com.brs.bookrentalsystem.dto.book.BookUpdateRequest;
import com.brs.bookrentalsystem.error.codes.ErrorCodes;
import com.brs.bookrentalsystem.error.exception.impl.NoSuchEntityFoundException;
import com.brs.bookrentalsystem.model.Author;
import com.brs.bookrentalsystem.model.Book;
import com.brs.bookrentalsystem.model.Category;
import com.brs.bookrentalsystem.repo.BookRepo;
import com.brs.bookrentalsystem.service.AuthorService;
import com.brs.bookrentalsystem.service.BookService;
import com.brs.bookrentalsystem.service.CategoryService;
import com.brs.bookrentalsystem.util.DateUtil;
import com.brs.bookrentalsystem.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final DateUtil dateUtil;
    private final FileStorageUtil fileStorageUtil;


    @Override
    public BookResponse saveBook(BookRequest request) {
        String fileStorageLocation = fileStorageUtil.getFileStorageLocation(request);

        request.setPhotoPath(fileStorageLocation);
        request.setIsbn(fileStorageUtil.formattedIsbn);
        Book book = toBook(request);
        book = bookRepo.save(book);

        // if saved then store file
        fileStorageUtil.saveMultipartFile(request.getMultipartFile(), fileStorageLocation);
        return toBookResponse(book);
    }

    // TODO:: LIKELY TO BREAK CHANGES
    private BookResponse toBookResponse(Book book) {

//        String publishedDate = dateUtil.dateToString(book.getPublishedDate());
        String publishedDate = dateUtil.localDateToHtmlDateFormat(book.getPublishedDate());

        List<Author> authors = book.getAuthor().stream()
                .toList();

        String fileName = book.getPhoto().substring(book.getPhoto().lastIndexOf("/") + 1);

        return BookResponse.builder()
                .id(book.getId())
                .bookName(book.getName())
                .isbn(book.getIsbn())
                .photoPath(book.getPhoto())
                .fileName(fileName)
                .rating(book.getRating())
                .stockCount(book.getStockCount())
                .totalPages(book.getTotalPages())
                .publishedDate(publishedDate)
                .category(book.getCategory())
                .authors(authors)
                .isActive(book.getIsActive())
                .build();
    }

    @Override
    public List<BookResponse> getSavedBooks() {
        List<Book> all = bookRepo.findAll();
        return all.stream()
                .map(this::toBookResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookById(Integer bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NoSuchEntityFoundException("Book not found", ErrorCodes.NOT_FOUND));
        return toBookResponse(book);
    }

    // for updating book
    @Override
    public BookRequest getBookRequestById(Integer bookId) {
        Book byId = bookRepo.findById(bookId).orElseThrow();
        return toBookRequest(byId);
    }

    BookRequest toBookRequest(Book book){
        List<Integer> authorList = book.getAuthor().stream()
                .map(Author::getId)
                .collect(Collectors.toList());
        String isbn = book.getIsbn().replace("-", "");
        return BookRequest.builder()
                .id(book.getId())
                .bookName(book.getName())
                .categoryId(book.getCategory().getId())
                .authorId(authorList)
                .isbn(isbn)
                .totalPages(book.getTotalPages())
                .stockCount(book.getStockCount())
                .rating(book.getRating())
                .multipartFile(fileStorageUtil.imagePathToMultipartFile(book.getPhoto()))
                .photoPath(book.getPhoto())
//                .publishedDate(String.valueOf(book.getPublishedDate()))
                .publishedDate(book.getPublishedDate())
                .build();
    }


    // handle exception
    @Override
    public Book getBookEntityById(Integer bookId) {
        return bookRepo.findById(bookId).orElseThrow();
    }


    // not used
    @Override
    public BookResponse updateBook(BookUpdateRequest updateRequest) {

        Category category = categoryService.getCategoryById(updateRequest.getCategoryId());
        Set<Author> authors = authorService.getAuthorAssociated(updateRequest.getAuthorId());
        LocalDate publishedDate = dateUtil.stringToDate(updateRequest.getPublishedDate());
        String fileStorageLocation = null;
        Book bookToUpdate = this.getBookEntityById(updateRequest.getId());
        if(!updateRequest.getMultipartFile().isEmpty()){
            fileStorageLocation = fileStorageUtil.getFileStorageLocation(updateRequest);
            bookToUpdate.setPhoto(fileStorageLocation);
        } else {
            // book has previous photo location
            bookToUpdate.getPhoto();
        }

        bookToUpdate.setId(updateRequest.getId());
        bookToUpdate.setIsbn(fileStorageUtil.changeToStandardIsbn(updateRequest.getIsbn()));
        bookToUpdate.setName(updateRequest.getBookName());
        bookToUpdate.setAuthor(authors);
        bookToUpdate.setCategory(category);
        bookToUpdate.setRating(updateRequest.getRating());
        bookToUpdate.setStockCount(updateRequest.getStockCount());
        bookToUpdate.setTotalPages(updateRequest.getTotalPages());
        bookToUpdate.setPublishedDate(publishedDate);

        bookToUpdate = bookRepo.save(bookToUpdate);

        // if new file sent then update the file
        if(!updateRequest.getMultipartFile().isEmpty()){
            fileStorageUtil.saveMultipartFile(updateRequest.getMultipartFile(), fileStorageLocation);
        }
        return toBookResponse(bookToUpdate);
    }

    @Override
    public Message deleteBookById(Integer bookId) {
        bookRepo.deleteById(bookId);
        Optional<Book> byId = bookRepo.findById(bookId);
        Message message = new Message();
        if(byId.isPresent()){
            message.setCode("ERROR");
            message.setMessage("Book with id : " + bookId + " could not be deleted");
        } else {
            message.setCode("DEL-BOOK");
            message.setMessage("Book with id : " + bookId + " deleted successfully");
        }
        return message;
    }

    @Override
    public Message reviveDeletedBookById(Integer bookId) {
        Message message = new Message();
        bookRepo.reviveDeletedBookById(bookId);
//
//        if(isBookRevived){
//            message.setCode("SUCCESS");
//            message.setMessage("Deleted Book revived successfully");
//        }else {
//            message.setCode("FAILURE");
//            message.setMessage("Deleted Book couldn't be revived");
//        }

        message.setCode("SUCCESS");
        message.setMessage("Deleted Book revived successfully");

        return message;
    }

    @Override
    public void updateStock(Integer bookId, Integer stockUpdateNumber) {
        Book book = getBookEntityById(bookId);
        book.setStockCount(book.getStockCount() + stockUpdateNumber);
        bookRepo.save(book);
    }

    @Override
    public List<BookResponse> getBooksAvailableOnStock() {
        List<Book> booksAvailableOnStock = bookRepo.findBookByStockCountIsGreaterThan(0);
        return booksAvailableOnStock.stream()
                .map(this::toBookResponse)
                .collect(Collectors.toList());
    }

    // create bookUpdateRequest for updating book * multipart file validation error
    @Override
    public BookUpdateRequest getBookUpdateRequest(Integer bookId) {

        Book bookEntityById = this.getBookEntityById(bookId);
        BookUpdateRequest bookUpdateRequest = toBookUpdateRequest(bookEntityById);
        return bookUpdateRequest;
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List<Book> allBooks = bookRepo.findAllBooks();
        return allBooks.stream()
                .map(this::toBookResponse)
                .collect(Collectors.toList());
    }

    BookUpdateRequest toBookUpdateRequest(Book book){
        List<Integer> authorList = book.getAuthor().stream()
                .map(Author::getId)
                .collect(Collectors.toList());
        String isbn = book.getIsbn().replace("-", "");
        return BookUpdateRequest.builder()
                .id(book.getId())
                .bookName(book.getName())
                .categoryId(book.getCategory().getId())
                .authorId(authorList)
                .isbn(isbn)
                .totalPages(book.getTotalPages())
                .stockCount(book.getStockCount())
                .rating(book.getRating())
                .multipartFile(null)
                .photoPath(book.getPhoto())
                .publishedDate(String.valueOf(book.getPublishedDate()))
                .build();
    }


    public Book toBook(BookRequest request) {

        Category category = categoryService.getCategoryById(request.getCategoryId());
        Set<Author> authors = authorService.getAuthorAssociated(request.getAuthorId());
//        LocalDate publishedDate = dateUtil.stringToDate(request.getPublishedDate());
        LocalDate publishedDate = dateUtil.formatLocalDate(request.getPublishedDate());

        String photoFilePath = null;
        Book book = new Book();
        if (request.getId() != null) {
            Book book1 = bookRepo.findById(request.getId()).orElseThrow();
            photoFilePath = book1.getPhoto();
        }

//        String imagePath = fileStorageUtil.saveMultipartFile(request.getMultipartFile(), fileStorageLocation);
        if (request.getId() != null) book.setId(request.getId());
        if (request.getBookName() != null) book.setName(request.getBookName());
        if (request.getTotalPages() != null) book.setTotalPages(request.getTotalPages());
        if (request.getIsbn() != null) book.setIsbn(request.getIsbn());
        book.setStockCount(request.getStockCount());
        book.setPublishedDate(publishedDate);
        if (request.getRating() != null) book.setRating(request.getRating());
        book.setCategory(category);
        book.setAuthor(authors);
        // creation
        if (request.getMultipartFile() != null) {
            book.setPhoto(request.getPhotoPath());
        } else {
            book.setPhoto(photoFilePath);
        }
        // update

        return book;
    }
}
