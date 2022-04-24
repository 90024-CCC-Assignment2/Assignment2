package controller;

import domain.Person;
import org.lightcouch.CouchDbClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/testcontroller")
public class TestController {

    @RequestMapping("/test1")
    public void test1(){
        System.out.println(123);
    }

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
}

