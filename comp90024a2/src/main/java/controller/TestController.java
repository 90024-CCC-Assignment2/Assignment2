package controller;

import domain.Data;
import domain.Feature;
import domain.JsonResult;
import domain.Person;
import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.TwitterService;

import java.util.List;

@Controller
@RequestMapping("/testcontroller")
public class TestController {
    @Autowired
    private TwitterService twitterService;

    @RequestMapping("/test2")
    public String test2(Model model){
        CouchDbClient couchDbClient = new CouchDbClient();
        String jsonQuery = " {\"selector\":{\"name\":\"bob\",\"age\":{\"$gt\":18}}}";
        List<Person> list = couchDbClient.findDocs(jsonQuery, Person.class);
        //System.out.println("Matched Count: "+ list.size());
        //list.forEach(ele -> System.out.println(ele));
        couchDbClient.shutdown();
        model.addAttribute("result",list);
        return "/WEB-INF/views/test/testview.jsp";
    }

    @RequestMapping(path = "/test3", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult test(String country){
        List<Feature> features = twitterService.searchByCountry(country);
        Data data = new Data();
        data.setFeatures(features);
        JsonResult success = JsonResult.success(data);
        return success;
    }
}

