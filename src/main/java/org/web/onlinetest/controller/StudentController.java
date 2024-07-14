package org.web.onlinetest.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.web.onlinetest.main.User;
import org.web.onlinetest.service.UserService;
import org.web.onlinetest.service.QuestionService;
import org.web.onlinetest.main.Question;
import org.web.onlinetest.main.QusOption;
import org.web.onlinetest.main.Course;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

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

    @PostMapping("/updatePassword")
    public String updatePassword(HttpSession session,
                                 @RequestParam("studentId") String studentId,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        logger.info("updatePassword");

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("message", "新密码和确认新密码不一致。");
            return "redirect:/changePassword";
        }

        boolean success = userService.updatePassword(studentId, currentPassword, newPassword);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "密码已成功更改！");
        } else {
            redirectAttributes.addFlashAttribute("message", "密码更改失败，请检查当前密码是否正确。");
        }
        return "redirect:/changePassword";
    }


    final String AVATAR_PATH = "D:\\Program\\OnlineTest\\src\\main\\resources\\static\\avatar\\";
    @PostMapping("/changeInfo")
    public String changeInfo(HttpSession session, Model model,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("uid") String uid,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/personalInfo";
        }
        String currentImgUrl =user.getImgurl();
        logger.info("changeInfo");
        if (file.isEmpty()) {
            logger.info("file is empty");
        }
        else {
            logger.info("try to upload file {}", file.getOriginalFilename());
            //随机数
            Random random = new Random();
           String newFileName = "uid"+uid+".jpg";
            // 保存文件到指定路径
            File dest = new File(AVATAR_PATH + newFileName);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                logger.error("文件上传失败", e);
            }
            currentImgUrl = "/avatar/" + newFileName;
        }
        user.setImgurl(currentImgUrl);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);

        userService.updateInfo(user);
        session.setAttribute("user", user);
        System.out.println("Have changed info!"+user.toString());

        return "student/personalInfo";
    }
}