package io.corecode.mywebsite.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Book;
import io.corecode.mywebsite.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public List<User> getAllUsers() throws Exception {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "user").asString();

        Type usersListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        Gson gson = new Gson();
        List<User> list = gson.fromJson(response.getBody(), usersListType);

        return list;
    }

    public User getUserById(int userId) throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "user/" + userId).asString();

        Gson gson = new Gson();
        User user = gson.fromJson(response.getBody(), User.class);

        return user;
    }

    public int updateUser(User user) throws Exception{
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put(MyConstants.url+"user/"+user.getUserId())
                .header("Content-Type", "application/json")
                .body("{\r\n    \"userName\": \""+user.getUserName()+"\",\r\n    \"password\": \""+user.getPassword()+"\",\r\n    \"role\": \""+user.getRole()+"\"\r\n}")
                .asString();
        return response.getStatus();
    }

    public int deleteUserById(int userId) throws Exception {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete(MyConstants.url+"user/"+userId)
                .asString();
        return response.getStatus();
    }
}
