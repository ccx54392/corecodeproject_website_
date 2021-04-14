package integrationTesting;

import com.google.gson.Gson;
import dataProviders.DataProvidersClass;
import integrationTest.RequestTemplate;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Book;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class BooksTest {

    @Test
    public void getBooks(){
        Response response = RequestTemplate.getRequest(MyConstants.url+"book");
        String responseString=response.asString();
        Gson gson = new Gson();
        Book[] books = gson.fromJson(responseString,Book[].class);
        for (int i = 0; i < books.length; i ++){
            Book book =  books[i];
            System.out.println(book.getBookId());
        }
    }

    @Test
    public void getBookbyId(){
        Response response = RequestTemplate.getRequest(MyConstants.url+"book/1");
        String responseString=response.asString();
        Gson gson = new Gson();
        Book book = gson.fromJson(responseString, Book.class);
        System.out.println(book.getBookId());
    }

    @Test(dataProviderClass = DataProvidersClass.class, dataProvider = "addBookParameters", enabled = false)
    public void addBook(String body){
        Response response = RequestTemplate.postRequest(MyConstants.url+"book", body);
        String responseString=response.asString();
        Gson gson = new Gson();
        Book book = gson.fromJson(responseString, Book.class);
        System.out.println(book.getBookId());
    }

    @Test(dataProviderClass = DataProvidersClass.class, dataProvider = "addBookParameters", enabled = false)
    public void updateBook(String body){
        Response response = RequestTemplate.postRequest(MyConstants.url+"book/50", body);

    }

    @Test()
    public void deleteBook(){
        Response response = RequestTemplate.deleteRequest(MyConstants.url+"book/1500");
        String responseString = response.asString();
        System.out.println(responseString);
    }

}
