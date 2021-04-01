package io.corecode.mywebsite.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Book;
import io.corecode.mywebsite.model.Publisher;
import io.corecode.mywebsite.model.User;
import io.corecode.mywebsite.model.Writer;
import io.corecode.mywebsite.repository.BookRepository;
import io.corecode.mywebsite.repository.PublisherRepository;
import io.corecode.mywebsite.repository.UserRepository;
import io.corecode.mywebsite.repository.WriterRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {
    @GetMapping("createAccount")
    public String showBooks(@ModelAttribute("user") User user) throws Exception {

        return "create_user";

    }


    @PostMapping("createUser")
    public String createUser(@ModelAttribute("user") User user, Map<String,Object> model) throws Exception {

        user.setRole("user");

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(MyConstants.url + "user/")
                .header("Content-Type", "application/json")
                .body("{\r\n    \"userName\": \"" + user.getUserName() + "\",\r\n    \"password\": \"" +
                        user.getPassword() + "\",\r\n    \"role\": \"" + user.getRole() + "\"\r\n}")
                .asString();

        user.setUserName("");
        user.setPassword("");
        model.put("message","User was successfully created, you can now log in");

        return "create_user";

    }

    @GetMapping("updateUserList")
    public String updateUserList(Map<String,Object> model) throws Exception {
        model.put("users",new UserRepository().getAllUsers());
        return "manage_users_admin";
    }

    @GetMapping("updateUser")
    public String updateUser(@RequestParam("userId") int userId, @ModelAttribute("user") User user, Map<String, Object> model) throws Exception {
        User dataUser = new UserRepository().getUserById(userId);

        user.setUserId(dataUser.getUserId());
        user.setUserName(dataUser.getUserName());
        user.setPassword(dataUser.getPassword());
        user.setRole(dataUser.getRole());

        List<String> roles = new ArrayList<String>();
        if(dataUser.getRole().equals("user")) {
            roles.add("admin");
        }
        else {
            roles.add("user");
        }
        roles.add(dataUser.getRole());
        model.put("roles",roles);

        return "manage_users_admin_update_user";
    }

    @PostMapping("updateUser")
    public String updateUser(@ModelAttribute("user") User user, Map<String,Object> model) throws Exception {
        User dataUser = new UserRepository().getUserById(user.getUserId());
        if(user.getPassword().equals("")){
            user.setPassword(dataUser.getPassword());
        }

        int status = new UserRepository().updateUser(user);

        List<String> roles = new ArrayList<String>();
        if(user.getRole().equals("user")) {
            roles.add("admin");
        }
        else {
            roles.add("user");
        }
        roles.add(user.getRole());
        model.put("roles",roles);

        if(status==200) {
            model.put("message", "User was updated successfully");
        }
        else{
            model.put("message","Unable to contact the backend server");
        }
        return "manage_users_admin_update_user";
    }

    @GetMapping("deleteUserList")
    public String deleteUserList(Map<String, Object> model) throws Exception {
        model.put("users", new UserRepository().getAllUsers());
        return "manage_users_admin";
    }

    @GetMapping("deleteUser")
    public String deleteUser(@RequestParam("userId") int userId, Map<String, Object> model) throws Exception {
        int status = new UserRepository().deleteUserById(userId);
        if(status==200){
            model.put("message","User deleted successfully");
        }
        else{
            model.put("message","Unable to contact the backend service");
        }

        model.put("users", new UserRepository().getAllUsers());
        return "manage_users_admin";
    }
    @GetMapping("updateUserIndividual")
    public String updateUserIndividual(@RequestParam("userId") int userId, @ModelAttribute("user") User user, Map<String, Object> model) throws Exception {
        User dataUser = new UserRepository().getUserById(userId);

        user.setUserId(dataUser.getUserId());
        user.setUserName(dataUser.getUserName());
        user.setPassword(dataUser.getPassword());
        user.setRole(dataUser.getRole());


        return "update_user_individual";
    }

    @PostMapping("updateUserIndividual")
    public String updateUserIndividual(@ModelAttribute("user") User user, Map<String,Object> model) throws Exception {
        User dataUser = new UserRepository().getUserById(user.getUserId());
        if(user.getPassword().equals("")){
            user.setPassword(dataUser.getPassword());
        }

        int status = new UserRepository().updateUser(user);

        if(status==200) {
            model.put("message", "User was updated successfully");
        }
        else{
            model.put("message","Unable to contact the backend server");
        }
        return "update_user_individual";
    }

    @GetMapping("myReviews")
    public String myReviews(@RequestParam("userId") int userId, Map<String, Object> model) throws Exception {
        User user = new UserRepository().getUserById(userId);
        model.put("user",user);
        model.put("userId",userId);
        model.put("bookId",0);
        if(user.getReviews().size()==0){
            model.put("message","You do not have any review yet");
        }
        return "manage_reviews_user";
    }
}
