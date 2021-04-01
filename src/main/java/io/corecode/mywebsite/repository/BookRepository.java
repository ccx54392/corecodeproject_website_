package io.corecode.mywebsite.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Book;
import io.corecode.mywebsite.model.Review;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    public List<Book> getAllBooks() throws Exception {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "book").asString();

        Type booksListType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        Gson gson = new Gson();
        List<Book> list = gson.fromJson(response.getBody(), booksListType);

        return list;

    }

    public Book getBookById(int bookId) throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "book/" + bookId).asString();

        Gson gson = new Gson();
        Book book = gson.fromJson(response.getBody(), Book.class);

        return book;
    }

    public void createBook(Book book) throws Exception{
        String temp = book.getDescription().replaceAll("(\\r\\n)", " ");
        book.setDescription(temp);

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(MyConstants.url+"book/")
                .header("Content-Type", "application/json")
                .body("{\r\n    \"title\": \""+book.getTitle()+
                        "\",\r\n    \"writerId\": "+book.getWriterId()+
                        ",\r\n    \"cover\": \""+book.getCover()+
                        "\",\r\n    \"picture\": \""+book.getPicture()+
                        "\",\r\n    \"publisherId\": "+book.getPublisherId()+
                        ",\r\n    \"description\": \""+book.getDescription()+
                        "\"\r\n    }")
                .asString();

    }

    public void updateBook(Book book) throws Exception{
        String temp = book.getDescription().replaceAll("(\\r\\n)", " ");
        book.setDescription(temp);

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put(MyConstants.url+"book/"+book.getBookId())
                .header("Content-Type", "application/json")
                .body("{\r\n    \"title\":\""+book.getTitle()+
                        "\",\r\n    \"writerId\":"+book.getWriterId()+
                        ",\r\n    \"cover\": \""+book.getCover()+
                        "\",\r\n    \"picture\": \""+book.getPicture()+
                        "\",\r\n    \"publisherId\":"+book.getPublisherId()+
                        ",\r\n    \"description\":\""+book.getDescription()+
                        "\"\r\n}")
                .asString();

    }

    public int deleteBookById(int bookId) throws Exception{
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete(MyConstants.url+"book/"+bookId)
                .asString();
        return response.getStatus();

    }
}
