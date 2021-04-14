package dataProviders;

import org.json.JSONObject;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class DataProvidersClass {

    @DataProvider(name = "addBookParameters", parallel = true)
    public Iterator<Object[]> getParameters() {
        Collection<Object[]> data = new ArrayList<>();
        for (int i = 50; i < 52; i++){
            JSONObject bodyContent = new JSONObject();
            bodyContent.put("title","username"+i);
            bodyContent.put("writerId", 1);
            bodyContent.put("cover","cover"+i);
            bodyContent.put("picture","picture"+i);
            bodyContent.put("publisherId",1);
            bodyContent.put("description","description"+i);
            data.add(new Object[]{bodyContent.toString()});
        }
        return data.iterator();
    }
    
}
