package io.corecode.mywebsite.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Login;
import io.corecode.mywebsite.model.User;
import io.corecode.mywebsite.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @GetMapping("login")
    public String getLogin(@ModelAttribute("login") Login login) {

        return "login";
    }

    @GetMapping("home")
    public String home() {
        return "page_admin";
    }

    @PostMapping("login")
    public String doLogin(@ModelAttribute("login") Login login, Map<String, Object> model) throws Exception {


        if(login.getUserName().equals("")){
            model.put("errorMessage","User name can not be empty");
            return "login";
        }

        //Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(MyConstants.url + "user").asString();
        if (response.getStatus() == 200) {
            Type usersListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            Gson gson = new Gson();
            List<User> list = gson.fromJson(response.getBody(), usersListType);
            for (User s : list) {
                if (s.getUserName().equals(login.getUserName())) {
                    if (s.getPassword().equals(login.getPassword())) {
                        if (s.getRole().equals("admin")) {
                            //model.put("userId", s.getUserId());
                            model.put("errorMessage", "User logged in successfully");

                            return "page_admin";
                        } else {


                            model.put("books", new BookRepository().getAllBooks());



                            model.put("userId", s.getUserId());
                            model.put("errorMessage", "User logged in successfully");

                            return "page_user";
                        }
                    } else {
                        model.put("errorMessage", "The password is incorrect");
                        return "login";
                    }
                }
            }
            model.put("errorMessage", "The user name does not exist");
            return "login";
        } else {
            model.put("errorMessage", "Unable to contact the server");
            return "login";
        }


    }

}
