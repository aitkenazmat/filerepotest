package kz.railways.filerepo.filerepo;


import kz.railways.filerepo.filerepo.annotation.Protected;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class SecuredController {

    @GetMapping("/info")
    @Protected(accesses = {"MANAGER3;1;1","MANAGER3;1;2"})
    public String info (){
        return "you get secured info!!!";
    }


    @GetMapping("/info1")
    @Protected(accesses = {"MANAGER3;3;1","MANAGER3;6;2"})
    public String info1 (){
        return "you get secured info1";
    }

}
