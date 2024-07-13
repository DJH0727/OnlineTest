package org.web.onlinetest.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.web.onlinetest.main.User;

@Controller
@RequestMapping("/")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/studentHome")
    public String studentHome(HttpSession session) {
            return "student/studentHome";
    }
    @RequestMapping("/createExam")
    public String createExam(HttpSession session, Model model) {
        logger.info("create exam");
        return "student/createExam";

    }
    @RequestMapping("/myExam")
    public String myExam(HttpSession session, Model model) {
        logger.info("myExam");
        return "student/myExam";
    }

    @RequestMapping("/personalInfo")
    public String personInfo(HttpSession session, Model model) {
        logger.info("personInfo");
        return "student/personalInfo";
    }

    @RequestMapping("/changePassword")
    public String changePassword(HttpSession session, Model model) {
        logger.info("changePassword");
        return "student/changePassword";
    }

}
