package com.chenyilei.MyTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
public class GirlController {

    @Autowired
    private GirlRepository girlRepository;

    @GetMapping(value = "/girls")
    public List<Girl>  girlList(){
        return girlRepository.findAll();
    }

    @PostMapping("/girls")
    public Girl girlAdd(@RequestParam("cupSize") String s,
                        @RequestParam("age")Integer age){
        Girl girl = new Girl();
        girl.setAge(age);
        girl.setCupSize(s);
        return girlRepository.save(girl);
    }
}
