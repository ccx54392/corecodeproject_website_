package io.corecode.mywebsite.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Publisher;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PublisherRepository {
    public List<Publisher> getAllPublishers() throws Exception {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "publisher").asString();

        Type publisherListType = new TypeToken<ArrayList<Publisher>>() {
        }.getType();
        Gson gson = new Gson();
        List<Publisher> list = gson.fromJson(response.getBody(), publisherListType);

        return list;

    }

    public Publisher getPublisherById(int publisherId) throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "publisher/" + publisherId).asString();

        Gson gson = new Gson();
        Publisher publisher = gson.fromJson(response.getBody(), Publisher.class);

        return publisher;
    }
    public void updatePublisher(Publisher publisher) throws Exception{
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put(MyConstants.url+"publisher/"+ publisher.getPublisherId())
                .header("Content-Type", "application/json")
                .body("{\r\n    \"name\":\" "+publisher.getName() +"  \"\r\n}")
                .asString();
    }

}
