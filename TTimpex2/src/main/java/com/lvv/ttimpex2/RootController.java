package com.lvv.ttimpex2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@ApiIgnore
@Controller
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @GetMapping("/")
    public String root() {
//        log.info("root");
        return "redirect:report";
    }

    //    @Secured("ROLE_ADMIN")
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String getUsers() {
//        log.info("users");
        return "users";
    }

    @GetMapping("/login")
    public String login() {
//        log.info("login");
        return "login";
    }

    @GetMapping("/report")
    public String getReport() {
//        log.info("report");
        return "report";
    }

    @GetMapping("/employees")
    public String getEmployees() {
//        log.info("employees");
        return "employees";
    }

    @GetMapping("/employees/daysoff")
    public String getDaysOff() {
//        log.info("employees");
        return "daysoff";
    }

    @GetMapping("/scodes")
    public String getSCodes() {
//        log.info("employees");
        return "scodes";
    }
}
