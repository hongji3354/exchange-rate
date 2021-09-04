package dev.jihun.domain.exchangerate.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExchangeViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
