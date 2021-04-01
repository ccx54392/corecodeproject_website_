package io.corecode.mywebsite.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Publisher;
import io.corecode.mywebsite.model.Writer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WriterRepository {
    public List<Writer> getAllWriters() throws Exception {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "writer").asString();

        Type writerListType = new TypeToken<ArrayList<Writer>>() {
        }.getType();
        Gson gson = new Gson();
        List<Writer> list = gson.fromJson(response.getBody(), writerListType);

        return list;

    }

    public Writer getWriterById(int writerId) throws Exception {


        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "writer/" + writerId).asString();

        Gson gson = new Gson();
        Writer writer = gson.fromJson(response.getBody(), Writer.class);

        return writer;
    }

    public int updateWriter(Writer writer) throws Exception{
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put(MyConstants.url+"writer/"+writer.getWriterId())
                .header("Content-Type", "application/json")
                .body("{\r\n    \"name\": \""+writer.getName()+"\"\r\n}")
                .asString();
        return response.getStatus();
    }

    public int createWriter(Writer writer) throws Exception{
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(MyConstants.url+"writer/")
                .header("Content-Type", "application/json")
                .body("{\r\n    \"name\": \""+writer.getName()+"\"\r\n}")
                .asString();
        return response.getStatus();
    }

}
