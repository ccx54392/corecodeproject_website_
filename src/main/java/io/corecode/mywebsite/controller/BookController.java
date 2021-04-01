package io.corecode.mywebsite.controller;

import io.corecode.mywebsite.model.Book;
import io.corecode.mywebsite.model.Publisher;
import io.corecode.mywebsite.model.Review;
import io.corecode.mywebsite.model.Writer;
import io.corecode.mywebsite.repository.BookRepository;
import io.corecode.mywebsite.repository.PublisherRepository;
import io.corecode.mywebsite.repository.WriterRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class BookController {
    @GetMapping("showBooks")
    public String showBooks(@RequestParam("userId") int userId, Map<String, Object> model) throws Exception {

        model.put("userId", userId);
        model.put("books", new BookRepository().getAllBooks());
        return "page_user";

    }

    @GetMapping("showBook")
    public String showBook(@RequestParam("bookId") int bookId, @RequestParam("userId") int userId, @ModelAttribute("review") Review review, Map<String, Object> model) throws Exception {

        model.put("userId", userId);
        model.put("bookId", bookId);

        model.put("book", new BookRepository().getBookById(bookId));

        return "book";
    }

    @GetMapping("createBook")
    public String createBook(Map<String, Object> model,@ModelAttribute("book") Book book) throws Exception {
        model.put("writers",new WriterRepository().getAllWriters());
        model.put("publishers",new PublisherRepository().getAllPublishers());
        return "manage_books_admin_create_book";
    }

    @PostMapping("createBook")
    public String createBook(@ModelAttribute("book") Book book, Map<String,Object> model) throws Exception {
        new BookRepository().createBook(book);

        book.setTitle("");
        book.setWriterId(0);
        book.setPublisherId(0);
        book.setDescription("");

        model.put("message","Book was successfully created");

        return "manage_books_admin_create_book";
    }

    @GetMapping("updateBookList")
    public String updateBookList(Map<String, Object> model) throws Exception {
        model.put("books", new BookRepository().getAllBooks());
        return "manage_books_admin";
    }

    @GetMapping("updateBook")
    public String updateBookList(@RequestParam("bookId") int bookId, @ModelAttribute("book") Book book, Map<String, Object> model) throws Exception {
        Book dataBook = new BookRepository().getBookById(bookId);
        book.setBookId(bookId);
        book.setDescription(dataBook.getDescription());
        book.setTitle(dataBook.getTitle());
        book.setPublisherId(dataBook.getPublisherId());
        book.setWriterId(dataBook.getWriterId());

        List<Writer> writersList = new WriterRepository().getAllWriters();
        Writer tempWriter;
        for(int i=0;i<writersList.size();i++){
            if(writersList.get(i).getWriterId()==dataBook.getWriterId()){
                tempWriter = writersList.get(i);
                writersList.remove(i);
                writersList.add(tempWriter);
                break;
            }
        }
        model.put("writers",writersList);

        List<Publisher> publishersList = new PublisherRepository().getAllPublishers();
        for(int i=0;i<publishersList.size();i++){
            Publisher tempPublisher;
            if(publishersList.get(i).getPublisherId()==dataBook.getPublisherId()){
                tempPublisher=publishersList.get(i);
                publishersList.remove(i);
                publishersList.add(tempPublisher);
                break;
            }

        }
        model.put("publishers",publishersList);

        return "manage_books_admin_update_book";
    }

    @PostMapping("updateBook")
    public String updateBook(@ModelAttribute("book") Book book, Map<String,Object> model) throws Exception {

        new BookRepository().updateBook(book);

//        book.setBookId(0);
//        book.setTitle("");
//        book.setWriterId(0);
//        book.setPublisherId(0);
//        book.setDescription("");

        Book dataBook = new BookRepository().getBookById(book.getBookId());


        List<Writer> writersList = new WriterRepository().getAllWriters();
        Writer tempWriter;
        for(int i=0;i<writersList.size();i++){
            if(writersList.get(i).getWriterId()==dataBook.getWriterId()){
                tempWriter = writersList.get(i);
                writersList.remove(i);
                writersList.add(tempWriter);
                break;
            }
        }
        model.put("writers",writersList);

        List<Publisher> publishersList = new PublisherRepository().getAllPublishers();
        for(int i=0;i<publishersList.size();i++){
            Publisher tempPublisher;
            if(publishersList.get(i).getPublisherId()==dataBook.getPublisherId()){
                tempPublisher=publishersList.get(i);
                publishersList.remove(i);
                publishersList.add(tempPublisher);
                break;
            }

        }
        model.put("publishers",publishersList);

        model.put("message","Book was updated successfully");

        return "manage_books_admin_update_book";
    }

    @GetMapping("deleteBookList")
    public String deleteBookList(Map<String, Object> model) throws Exception {
        model.put("books", new BookRepository().getAllBooks());
        return "delete_book_select_book";
    }

    @GetMapping("deleteBook")
    public String deleteBook(@RequestParam("bookId") int bookId, Map<String, Object> model) throws Exception {
        int status = new BookRepository().deleteBookById(bookId);
        if(status==200){
            model.put("message","Book deleted successfully");
        }
        else{
            model.put("message","Unable to contact the backend service");
        }

        model.put("books", new BookRepository().getAllBooks());
        return "manage_books_admin";
    }

    @GetMapping("showBooksGuest")
    public String showBooksGuest(Map<String, Object> model) throws Exception {
        model.put("books", new BookRepository().getAllBooks());
        return "page_guest";
    }
    @GetMapping("/")
    public String showBooksMain(Map<String, Object> model) throws Exception {
        model.put("books", new BookRepository().getAllBooks());
        return "page_guest";
    }

    @GetMapping("showBookGuest")
    public String showBook(@RequestParam("bookId") int bookId, Map<String, Object> model) throws Exception {

        model.put("book", new BookRepository().getBookById(bookId));
        return "book_guest";
    }

}
