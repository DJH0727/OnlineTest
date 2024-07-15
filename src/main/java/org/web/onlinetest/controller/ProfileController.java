package org.web.onlinetest.controller;


import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.web.onlinetest.main.User;
import org.web.onlinetest.service.UserService;

@Controller
public class ProfileController {

    final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String KEY_USER = "user";

    @Autowired
    UserService userService;

    @GetMapping("menu/account")
    public String toAccount(HttpSession session , Model model) {
        User user = (User) session.getAttribute(KEY_USER);
        logger.info("GetMapping menu/account");
        if (user == null) {
            return "redirect:/signin";
        }
        model.addAttribute("user", user);
        return "menu/account";
    }

    @GetMapping("menu/edit-account")
    public String account(HttpSession session , Model model) {
        logger.info("GetMapping edit-account");
        User user = (User) session.getAttribute(KEY_USER);
        if (user == null) {
            return "redirect:/signin";
        }
        model.addAttribute("user", user);
        model.addAttribute("message", "modify");
        return "menu/account";
    }

    @PostMapping("menu/save-account")
    public String editAccount(@RequestParam("email") String email ,@RequestParam("name") String name,
                              @RequestParam("phone") String phone,HttpSession session, Model model){
        User user = (User) session.getAttribute(KEY_USER);
        logger.info("PostMapping save-account");
        if (user == null) {
            return "redirect:/signin";
        }
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phone);
        session.setAttribute(KEY_USER, user);


        model.addAttribute("user", user);
        return "redirect:/menu/account";
    }


}
