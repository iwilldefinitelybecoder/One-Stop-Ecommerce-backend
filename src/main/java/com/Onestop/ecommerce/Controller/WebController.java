package com.Onestop.ecommerce.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @RequestMapping(value = {"/", "/{path:[^\\.]*}", "/{path:[^\\.]*}/**"})
    public String forwardReactApp() {
        return "forward:/index.html";
    }
}
