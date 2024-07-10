package org.web.onlinetest.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.web.onlinetest.main.Course;
import org.web.onlinetest.main.Question;
import org.web.onlinetest.main.QusOption;
import org.web.onlinetest.main.User;
import org.web.onlinetest.service.AdminService;
import org.web.onlinetest.service.QuestionService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
public class AdminController {

    //日志记录
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AdminService adminService;
    @Autowired
    QuestionService questionService;

    @RequestMapping("/admin/adminHome")
    public String adminHome() {

        return "admin/adminHome";

    }

    @RequestMapping("usermanage")
    public String userManage(Model model) {
        List<User> userList = adminService.findAllUser();
        model.addAttribute("users", userList);
        model.addAttribute("menu", 1);
        return "admin/userManage";
    }

    @GetMapping("/userManage")
    public String toUserManage( Model model) {
        List<User> userList = adminService.findAllUser();
        model.addAttribute("users", userList);
        model.addAttribute("menu", 1);
        return "admin/userManage";
    }

    @RequestMapping("deleteUser")
    public String deleteUser(@RequestParam("userid") String userId) {
        logger.info("trying to delete user id: {}" , userId);
        adminService.deleteUser(userId);
        return "redirect:/userManage";
    }
    @RequestMapping("resetPassword")
    public String resetPassword(@RequestParam("userid") String userId) {
        logger.info("trying to reset password userid: {}" , userId);
        adminService.resetPwd(userId);
        return "redirect:/userManage";
    }


    @RequestMapping("addUser")
    public String addUser() {

        logger.info("trying to add user");
        return "redirect:/userManage";
    }

    @PostMapping("/editUser")
    public String editUser(@RequestParam("userid_edit") String uid,
                           @RequestParam("username_edit") String name,
                           @RequestParam("email_edit") String email,
                           @RequestParam("phone_edit") String phone) {
        System.out.println("uid: " + uid);
        System.out.println("name: " + name);
        System.out.println("email: " + email);
        System.out.println("phone: " + phone);
        User user = new User();
        user.setUid(uid);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        adminService.updateUser(user);
        return "redirect:/userManage";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam("uid_add") String uid,
                          @RequestParam("pwd_add") String pwd,
                          @RequestParam("name_add") String name,
                          RedirectAttributes redirectAttributes) {

        if(uid.isEmpty() || name.isEmpty() || pwd.isEmpty())
        {
            redirectAttributes.addFlashAttribute("error","UID、姓名、密码不能为空");
            return "redirect:/userManage";
        }
        User user = new User();
        user.setName(name);
        user.setUid(uid);
        user.setPwd(pwd);
        user.setRole(1);
       boolean flag = adminService.addUser(user);
        if(flag){
            return "redirect:/userManage";
        }
        else{
            redirectAttributes.addAttribute("error","添加失败,用户"+uid+"已存在");
        }
        return "redirect:/userManage";
    }

    @RequestMapping("/searchUser")
    public String searchUser(@RequestParam("uid_search") String uid, Model model) {
        //先查询全部用户
        List<User> userList = adminService.findAllUser();

        if (uid.isEmpty()) {
            model.addAttribute("users", userList);
            model.addAttribute("menu", 1);
            return "admin/userManage";
        }
        List<User> searchList =new ArrayList<>();
        Pattern pattern = Pattern.compile(".*"+uid+".*");
        //正则表达式匹配用户
        for (User user : userList) {
            if (pattern.matcher(user.getUid()).matches()) {
                searchList.add(user);
            }
        }

        model.addAttribute("users", searchList);
        model.addAttribute("menu", 1);
        return "admin/userManage";
    }
    ///
    ///
    ///
    ///
    ///
    @GetMapping("/addQuestion")
    public String addQuestion(Model model) {
        logger.info("trying to add question");
        model.addAttribute("menu", 2);
        List<Course> courses = questionService.getAllCourses();
        model.addAttribute("courses", courses);
        return "admin/addQuestion";
    }
    @PostMapping("/addSingleChoiceQuestion")
    public String addSingleChoiceQuestion(@RequestParam("question1") String question1,
                                          @RequestParam("optionA1") String optionA1,
                                          @RequestParam("optionB1") String optionB1,
                                          @RequestParam("optionC1") String optionC1,
                                          @RequestParam("optionD1") String optionD1,
                                          @RequestParam("answer1") String answer1,
                                          @RequestParam("courseId") int courseId)
    {
        Question question = new Question();
        question.setCid(courseId);
        question.setQtype(1);
        question.setQtext(question1);
        question.setAnswer(answer1);
        List<QusOption> options = new ArrayList<>();
        QusOption optionA = new QusOption();
        optionA.setOp(1);optionA.setOptext(optionA1);
        options.add(optionA);
        QusOption optionB = new QusOption();
        optionB.setOp(2);optionB.setOptext(optionB1);
        options.add(optionB);
        QusOption optionC = new QusOption();
        optionC.setOp(3);optionC.setOptext(optionC1);
        options.add(optionC);
        QusOption optionD = new QusOption();
        optionD.setOp(4);optionD.setOptext(optionD1);
        options.add(optionD);
        question.setOptions(options);
        questionService.addQuestion(question);
        return "redirect:/addQuestion";
    }
    @PostMapping("/addMultipleChoiceQuestion")
    public String addMultipleChoiceQuestion(@RequestParam("question2") String question2,
                                            @RequestParam("optionA2") String optionA2,
                                            @RequestParam("optionB2") String optionB2,
                                            @RequestParam("optionC2") String optionC2,
                                            @RequestParam("optionD2") String optionD2,
                                            @RequestParam("answer2") String answer2,
                                            @RequestParam("courseId2") int courseId) {


        System.out.println("question2: " + question2);
        System.out.println("optionA2: " + optionA2);
        System.out.println("optionB2: " + optionB2);
        System.out.println("optionC2: " + optionC2);
        System.out.println("optionD2: " + optionD2);
        System.out.println("answer2: " + answer2);
        System.out.println("courseId: " + courseId);
        return "redirect:/addQuestion";
    }
    @RequestMapping("/quesManage")
    public String quesManage(Model model) {
        logger.info("trying to ques manage");
        model.addAttribute("menu", 3);
        return "admin/quesManage";
    }




}
