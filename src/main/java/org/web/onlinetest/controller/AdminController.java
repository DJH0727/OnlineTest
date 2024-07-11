package org.web.onlinetest.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
import java.util.Objects;
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
                                          @RequestParam("courseId") int courseId,
                                          RedirectAttributes redirectAttributes)
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
        boolean flag = questionService.addQuestion(question);
        if(flag)
            redirectAttributes.addFlashAttribute("message","添加成功!");
        else
            redirectAttributes.addFlashAttribute("message","添加失败!");
        return "redirect:/addQuestion";
    }
    @PostMapping("/addMultipleChoiceQuestion")
    public String addMultipleChoiceQuestion(@RequestParam("question2") String question2,
                                            @RequestParam("optionA2") String optionA2,
                                            @RequestParam("optionB2") String optionB2,
                                            @RequestParam("optionC2") String optionC2,
                                            @RequestParam("optionD2") String optionD2,
                                            @RequestParam("courseId2") int courseId,
                                            @RequestParam("mul_answer") String answer,
                                            RedirectAttributes redirectAttributes)
    {
        if(answer.isEmpty())
        {
            redirectAttributes.addFlashAttribute("message","请选择正确答案!");
            return "redirect:/addQuestion";
        }
        Question question = new Question();
        question.setCid(courseId);
        question.setQtype(2);
        question.setQtext(question2);
        question.setAnswer(answer);
        List<QusOption> options = new ArrayList<>();
        QusOption optionA = new QusOption();
        optionA.setOp(1);optionA.setOptext(optionA2);
        options.add(optionA);
        QusOption optionB = new QusOption();
        optionB.setOp(2);optionB.setOptext(optionB2);
        options.add(optionB);
        QusOption optionC = new QusOption();
        optionC.setOp(3);optionC.setOptext(optionC2);
        options.add(optionC);
        QusOption optionD = new QusOption();
        optionD.setOp(4);optionD.setOptext(optionD2);
        options.add(optionD);
        question.setOptions(options);
        boolean flag = questionService.addQuestion(question);
        if(flag)
            redirectAttributes.addFlashAttribute("message","添加成功!");
        else
            redirectAttributes.addFlashAttribute("message","添加失败!");

        return "redirect:/addQuestion";
    }

    @PostMapping("/addTrueFalseQuestion")
    public String addTrueFalseQuestion(@RequestParam("question3") String question,
                                       @RequestParam("answer3") String answer,
                                       @RequestParam("courseId3") int courseId,
                                       RedirectAttributes redirectAttributes,
                                       HttpSession session)
        {
            Question NewQuestion = new Question();
            NewQuestion.setCid(courseId);
            NewQuestion.setQtype(3);
            NewQuestion.setQtext(question);
            NewQuestion.setAnswer(answer);
            List<QusOption> options = new ArrayList<>();
            QusOption optionA = new QusOption();
            optionA.setOp(1);optionA.setOptext("True");
            options.add(optionA);
            QusOption optionB = new QusOption();
            optionB.setOp(2);optionB.setOptext("False");
            options.add(optionB);
            NewQuestion.setOptions(options);

            boolean flag = questionService.addQuestion(NewQuestion);
            if(flag)
                redirectAttributes.addFlashAttribute("message","添加成功!");
            else
                redirectAttributes.addFlashAttribute("message","添加失败!");

            return "redirect:/addQuestion";
        }

    final String currentQList = "currentQList";
    final int pageSize = 5;
    @RequestMapping("/quesManage")
    public String quesManage(Model model, HttpSession session,Integer page) {
        List<Question> AllQuestions = new ArrayList<>();
        List<Course> courses = questionService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("menu", 3);
        AllQuestions = questionService.findAllQuestions();
        session.setAttribute(currentQList, AllQuestions);

        List<Question> questions = new ArrayList<>();
        if(page==null) page = 1;
        int start = (page-1)*pageSize;
        int end = start+pageSize;
        if(AllQuestions.size()<end) end = AllQuestions.size();
        for(int i=start;i<end;i++)
        {
            questions.add(AllQuestions.get(i));
        }

        model.addAttribute("pageNum", page);
        model.addAttribute("totalPage", (AllQuestions.size()+pageSize-1)/pageSize);
        model.addAttribute("AllQCount", AllQuestions.size());
        model.addAttribute("prevPage", Math.max(page - 1, 1));
        model.addAttribute("nextPage", Math.min(page + 1, (AllQuestions.size()+pageSize-1)/pageSize));

        logger.info("trying to ques manage page{}", page);
        model.addAttribute("questions", questions);
        return "admin/quesManage";
    }
    @RequestMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("qid") int qid,HttpSession session) {
        logger.info("trying to delete question id: {}", qid);
        boolean flag = questionService.deleteQuestion(qid);

        return "redirect:/quesManage";
    }

    //逻辑上是先搜索后筛选，能用就行（bushi）
    @RequestMapping("/searchQuestion")
    public String searchQuestion(@RequestParam("searchText") String questionText,
                                 @RequestParam("courseId") int courseId,
                                 @RequestParam("questionType") int questionType,
                                 Model model, HttpSession session, Integer page) {

        logger.info("trying to search question by question: {}", questionText);
        List<Course> courses = questionService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("menu", 3);
        List<Question> questions = new ArrayList<>();
        questions = questionService.searchQuestionByKeyword(questionText, courseId, questionType);
        List<Question> searchQList = new ArrayList<>();
        if(page==null) page = 1;
        int start = (page-1)*pageSize;
        int end = start+pageSize;
        if(questions.size()<end) end = questions.size();
        for(int i=start;i<end;i++)
        {
            searchQList.add(questions.get(i));
        }
        model.addAttribute("pageNum", page);
        model.addAttribute("totalPage", (questions.size()+pageSize-1)/pageSize);
        model.addAttribute("AllQCount", questions.size());
        model.addAttribute("prevPage", Math.max(page - 1, 1));
        model.addAttribute("nextPage", Math.min(page + 1, (questions.size()+pageSize-1)/pageSize));
        model.addAttribute("questions", searchQList);
        model.addAttribute("ListMode", "search");
        return "admin/quesManage";
    }






}
