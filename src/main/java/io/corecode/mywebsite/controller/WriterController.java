package io.corecode.mywebsite.controller;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.corecode.mywebsite.MyConstants;
import io.corecode.mywebsite.model.Writer;
import io.corecode.mywebsite.repository.WriterRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class WriterController {

    @GetMapping("createWriter")
    public String createWriter(Map<String, Object> model, @ModelAttribute("writer") Writer writer) throws Exception {
        return "manage_writers_admin_create_writer";
    }

    @PostMapping("createWriter")
    public String createBook(@ModelAttribute("writer") Writer writer, Map<String,Object> model) throws Exception {

        int status = new WriterRepository().createWriter(writer);

        if(status==201){
            model.put("message","Writer was successfully created");
            writer.setName("");
        }
        else{
            model.put("message","Unable to contact the backend service");
        }

        return "manage_writers_admin_create_writer";
    }

    @GetMapping("deleteWriter")
    public String deleteWriter(@RequestParam("writerId") int writerId, Map<String, Object> model) throws Exception {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete(MyConstants.url+"writer/"+writerId)
                .asString();

        if(response.getStatus()==200){
            model.put("message","Writer was deleted successfully");
        }
        else{
            model.put("message","Unable to contact the backend service");
        }

        model.put("writers", new WriterRepository().getAllWriters());
        return "manage_writers_admin";
    }

    @GetMapping("updateWriter")
    public String updateWriter(@RequestParam("writerId") int writerId, @ModelAttribute("writer") Writer writer, Map<String, Object> model) throws Exception {
        Writer dataWriter = new WriterRepository().getWriterById(writerId);
        writer.setWriterId(dataWriter.getWriterId());
        writer.setName(dataWriter.getName());
        return "manage_writers_admin_update_writer";

        }

    @GetMapping("manageWriters")
    public String updateWriterList(Map<String, Object> model) throws Exception {
        model.put("writers", new WriterRepository().getAllWriters());
        return "manage_writers_admin";
    }
    @PostMapping("updateWriter")
    public String updateWriter(@ModelAttribute("writer") Writer writer, Map<String,Object> model) throws Exception {

        int status = new WriterRepository().updateWriter(writer);

        if(status==200) {
            model.put("message", "Writer was updated successfully");
        }
        else{
            model.put("message","Unable to contact the backend service");
        }
        return "manage_writers_admin_update_writer";
    }

}
