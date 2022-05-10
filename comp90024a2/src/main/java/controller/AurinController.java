package controller;

import domain.AurinData;
import domain.JsonResult;
import domain.RequestedJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.IAurinService;
import utils.Constant;

import java.util.List;

@Controller
@RequestMapping("/aurin")
@CrossOrigin(origins = "*")
public class AurinController {
    @Autowired
    private IAurinService aurinServiceImpl;

    @RequestMapping(path = "/count-all-country", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult countAllAurinByCountry() {
        List<List<Integer>> source = aurinServiceImpl.countAllAurinByCountry();
        AurinData aurinData = new AurinData(Constant.AURIN_COUNTRY_LIST, source);
        RequestedJson restaurant_number = new RequestedJson("Restaurant Number", aurinData);
        JsonResult success = JsonResult.success(restaurant_number);
        return success;
    }
}
