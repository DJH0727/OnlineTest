package org.web.onlinetest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StudentController {
    @RequestMapping("/student/studentHome")
    public String studentHome() {
        return "/student/studentHome";
    }
}
