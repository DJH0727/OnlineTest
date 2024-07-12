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



    final Integer UserPageSize = 7;
    @RequestMapping("/userManage")
    public String toUserManage( Model model,Integer page) {
        List<User> userList = adminService.findAllUser();

        model.addAttribute("menu", 1);
        if (page == null||page<=0) {
            page = 1;
        }
        int start = (page - 1) * UserPageSize;
        int end = start + UserPageSize;
        List<User> userPageList = userList.subList(start, end > userList.size() ? userList.size() : end);
        model.addAttribute("users", userPageList);
        model.addAttribute("pageNum", page);
        model.addAttribute("totalPage", (userList.size()+UserPageSize-1)/UserPageSize);
        model.addAttribute("AllQCount", userList.size());
        model.addAttribute("prevPage", Math.max(1, page - 1));
        model.addAttribute("nextPage", Math.min((userList.size()+UserPageSize-1)/UserPageSize, page + 1));
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
    public String searchUser(@RequestParam("uid_search") String uid,Integer page, Model model) {
        logger.info("trying to search user by uid: {}", uid);
        List<User> userList = adminService.findUserByUid(uid);

        if (page == null||page<=0) {
            page = 1;
        }
        int start = (page - 1) * UserPageSize;
        int end = start + UserPageSize;
        List<User> userPageList = userList.subList(start, end > userList.size() ? userList.size() : end);
        model.addAttribute("uid_search", uid);
        model.addAttribute("users", userPageList);
        model.addAttribute("pageNum", page);
        model.addAttribute("totalPage", (userList.size()+UserPageSize-1)/UserPageSize);
        model.addAttribute("AllQCount", userList.size());
        model.addAttribute("prevPage", Math.max(1, page - 1));
        model.addAttribute("nextPage", Math.min((userList.size()+UserPageSize-1)/UserPageSize, page + 1));
        model.addAttribute("menu", 1);
        model.addAttribute("mode", "search");
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
    final int QuestionPageSize = 5;
    @RequestMapping("/quesManage")
    public String quesManage(Model model, HttpSession session,Integer page) {
        List<Question> AllQuestions = new ArrayList<>();
        List<Course> courses = questionService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("menu", 3);
        AllQuestions = questionService.findAllQuestions();
        session.setAttribute(currentQList, AllQuestions);

        List<Question> questions = new ArrayList<>();
        if(page==null||page<=0) page = 1;
        int start = (page-1)*QuestionPageSize;
        int end = start+QuestionPageSize;
        if(AllQuestions.size()<end) end = AllQuestions.size();
        for(int i=start;i<end;i++)
        {
            questions.add(AllQuestions.get(i));
        }

        model.addAttribute("pageNum", page);
        model.addAttribute("totalPage", (AllQuestions.size()+QuestionPageSize-1)/QuestionPageSize);
        model.addAttribute("AllQCount", AllQuestions.size());
        model.addAttribute("prevPage", Math.max(page - 1, 1));
        model.addAttribute("nextPage", Math.min(page + 1, (AllQuestions.size()+QuestionPageSize-1)/QuestionPageSize));

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


    @RequestMapping("/searchQuestion")
    public String searchQuestion(@RequestParam(value = "searchText", required = false) String searchText,
                                 @RequestParam(value = "courseId" , required = false) Integer courseId,
                                 @RequestParam(value = "questionType" , required = false) Integer questionType,
                                 Model model, HttpSession session, Integer page) {

        logger.info("Post trying to search question by question: {}, courseId: {}, questionType: {}", searchText, courseId, questionType);
        List<Course> courses = questionService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("menu", 3);
        List<Question> questions = new ArrayList<>();
        questions = questionService.searchQuestionByKeyword(searchText, courseId, questionType);
        List<Question> searchQList = new ArrayList<>();
        if(page==null||page<=0) page = 1;
        int start = (page-1)*QuestionPageSize;
        int end = start+QuestionPageSize;
        if(questions.size()<end) end = questions.size();
        for(int i=start;i<end;i++)
        {
            searchQList.add(questions.get(i));
        }

        model.addAttribute("searchText", searchText);
        model.addAttribute("courseId", courseId);
        model.addAttribute("questionType", questionType);

        model.addAttribute("pageNum", page);
        model.addAttribute("totalPage", (questions.size()+QuestionPageSize-1)/QuestionPageSize);
        model.addAttribute("AllQCount", questions.size());
        model.addAttribute("prevPage", Math.max(page - 1, 1));
        model.addAttribute("nextPage", Math.min(page + 1, (questions.size()+QuestionPageSize-1)/QuestionPageSize));
        model.addAttribute("questions", searchQList);
        model.addAttribute("ListMode", "search");


        return "admin/quesManage";
    }


    @RequestMapping("/editMultipleChoiceQuestion")
    public String editMultipleChoiceQuestion(@RequestParam("mul_qid") int qid,
                                             @RequestParam("mul_question")String question,
                                             @RequestParam("mul_optionA")String optionA,
                                             @RequestParam("mul_optionB")String optionB,
                                             @RequestParam("mul_optionC")String optionC,
                                             @RequestParam("mul_optionD")String optionD,
                                             @RequestParam("mul_answer")String answer,
                                             @RequestParam("mul_courseId")int courseId,
                                             HttpSession session) {

       logger.info("trying to edit multiple choice question id: {}", qid);
       boolean flag = questionService.updateQuestion(qid, question, optionA, optionB, optionC, optionD, answer, courseId);
       if(flag)logger.info("edit multiple choice question id: {} success", qid);
       else logger.info("edit multiple choice question id: {} fail", qid);

       return "redirect:/quesManage";
    }
    @RequestMapping("/editSingleChoiceQuestion")
    public String editSingleChoiceQuestion(@RequestParam("single_qid") int qid,
                                           @RequestParam("single_question")String question,
                                           @RequestParam("single_optionA")String optionA,
                                           @RequestParam("single_optionB")String optionB,
                                           @RequestParam("single_optionC")String optionC,
                                           @RequestParam("single_optionD")String optionD,
                                           @RequestParam("single_answer")String answer,
                                           @RequestParam("single_courseId")int courseId)
    {
        logger.info("trying to edit single choice question id: {}", qid);
        boolean flag = questionService.updateQuestion(qid, question, optionA, optionB, optionC, optionD, answer, courseId);
        if(flag)logger.info("edit single choice question id: {} success", qid);
        else logger.info("edit single choice question id: {} fail", qid);
        return "redirect:/quesManage";
    }
    @RequestMapping("/editJudgeQuestion")
    public String editJudgeQuestion(@RequestParam("judge_qid") int qid,
                                    @RequestParam("judge_question")String question,
                                    @RequestParam("judge_answer")String answer,
                                    @RequestParam("judge_courseId")int courseId)
    {
        logger.info("trying to edit judge question id: {}", qid);
        boolean flag = questionService.updateQuestion(qid, question, answer, courseId);
        if(flag)logger.info("edit judge question id: {} success", qid);
        else logger.info("edit judge question id: {} fail", qid);
        return "redirect:/quesManage";
    }









}
