package controller;

import domain.*;
import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.StreamingTwitterService;

import java.util.List;

@Controller
@RequestMapping("/streamingtweet")
@CrossOrigin(origins = "*")
public class StreamingTweetController {
    @Autowired
    private StreamingTwitterService twitterService;

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

    /**
     * Request all streaming tweets by the given country
     * @param country
     * @return
     */
    @RequestMapping(path = "/country", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult test(String country){
        List<Feature> features = twitterService.searchByCountry(country);
        Data data = new Data();
        data.setFeatures(features);
        RequestedJson geojson = new RequestedJson("geojson", data);
        JsonResult success = JsonResult.success(geojson);
        return success;
    }
}

