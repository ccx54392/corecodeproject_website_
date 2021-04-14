package io.corecode.mywebsite.controller;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Publisher;
import io.corecode.mywebsite.repository.PublisherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class PublisherController {

    @GetMapping("createPublisher")
    public String createBook(Map<String, Object> model, @ModelAttribute("publisher") Publisher publisher) throws Exception {

        return "manage_publishers_admin_create_publisher";
    }

    @PostMapping("createPublisher")
    public String createBook(@ModelAttribute("publisher") Publisher publisher, Map<String,Object> model) throws Exception {

        //Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(MyConstants.url+"publisher/")
                .header("Content-Type", "application/json")
                .body("{\r\n    \"name\":\" "+publisher.getName() +"\"\r\n}\r\n")
                .asString();

        model.put("message","Publisher was successfully created");

        publisher.setName("");
        return "manage_publishers_admin_create_publisher";
    }


    @GetMapping("deletePublisher")
    public String deleteBook(@RequestParam("publisherId") int publisherId, Map<String, Object> model) throws Exception {

        //Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete(MyConstants.url+"publisher/"+publisherId)
                .asString();

        if(response.getStatus()==200){
            model.put("message","Publisher deleted successfully");
        }
        else{
            model.put("message","Unable to contact the backend service");
        }

        model.put("publishers", new PublisherRepository().getAllPublishers());
        return "manage_publishers_admin";
    }

    @GetMapping("updatePublisher")
    public String updatePublisher(@RequestParam("publisherId") int publisherId, @ModelAttribute("publisher") Publisher publisher, Map<String, Object> model) throws Exception {
        Publisher dataPublisher = new PublisherRepository().getPublisherById(publisherId);
        publisher.setPublisherId(dataPublisher.getPublisherId());
        publisher.setName(dataPublisher.getName());


        return "manage_publishers_admin_update_publisher";

        }

    @GetMapping("updatePublisherList")
    public String updatePublisherList(Map<String, Object> model) throws Exception {

        model.put("publishers", new PublisherRepository().getAllPublishers());
        return "manage_publishers_admin";
    }
    @PostMapping("updatePublisher")
    public String updateBook(@ModelAttribute("publisher") Publisher publisher, Map<String,Object> model) throws Exception {

        new PublisherRepository().updatePublisher(publisher);


        model.put("message","Publisher was updated successfully");

        return "manage_publishers_admin_update_publisher";
    }

}
