package org.web.onlinetest.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.onlinetest.main.User;
import org.web.onlinetest.service.UserService;

@Controller
@RequestMapping("/")
public class SignInController {
    final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String KEY_USER = "user";

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute(KEY_USER);
        if (user == null) {
            logger.info("user is null");
            return "index";
        }
        model.addAttribute("user", user);
        return "index";
    }
    @RequestMapping("/signin")
    public String toLogin() {
        logger.info("Request to signin");
        return "signin";
    }
    @RequestMapping("/profile")
    public String toProfile() {
        logger.info("Request to profile");
        return "profile";
    }
    @PostMapping("/signin")
    public String signIn(@RequestParam("userid") String UserID, @RequestParam("password") String password,
                         HttpSession session, Model model) {

        logger.info("POST /signin");

        //登录
        User user = userService.signIn(UserID, password);

        if (user!= null) {
            session.setAttribute(KEY_USER, user);
            model.addAttribute("user", user);
            if(user.getRole()==0)
                return "redirect:/adminHome";
            else
                return "redirect:/studentHome";

        } else {
            model.addAttribute("userid", UserID);
            model.addAttribute("error", "Invalid userid or password");
            return "signin";
        }
    }

    //登出
    @GetMapping("/signout")
    public String signout(HttpSession session) {
        session.removeAttribute(KEY_USER);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute(KEY_USER);

        if (user == null) {
            return "redirect:/signin";
        }
        logger.info(user.toString());
        model.addAttribute("user", user);
        return "profile";
    }


}
