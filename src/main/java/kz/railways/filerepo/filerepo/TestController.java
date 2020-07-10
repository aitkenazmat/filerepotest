package kz.railways.filerepo.filerepo;

import kz.railways.filerepo.filerepo.annotation.Protected;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/info")
    @Protected(accesses = {"MANAGER3;1;1","MANAGER3;1;2"})
    public String info (){
        return "you get secured infotest!!!";
    }


    @GetMapping("/info1")
    @Protected(accesses = {"MANAGER3;6;1","MANAGER3;8;2"})
    public String infotest1 (){
        return "you get secured infotest1111!!!";
    }
}
