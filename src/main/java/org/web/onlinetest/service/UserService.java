package org.web.onlinetest.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.web.onlinetest.main.User;

import java.util.List;

@Component
@Transactional
public class UserService {

    //日志记录
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;

    //单个用户
    RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

    //查询用户
    public User getUserById(String uid) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(
                    "SELECT * FROM users,user_info WHERE users.uid = user_info.uid AND users.uid = ?", userRowMapper, uid);
        }
        catch (Exception e) {
            logger.info("The user {} not found", uid);
        }
        return user;
    }
    //查询所有用户
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users,user_info WHERE users.uid = user_info.uid", userRowMapper);
    }


    //登录
    public User signIn(String uid, String pwd) {
        logger.info("try login by {}...", uid);
        User user = getUserById(uid);
        logger.info("user {} found", user);
        if (user == null) {
            logger.info("user {} not found", uid);
            return null;
        }
        if (user.getPwd().equals(pwd)) {
            return user;
        }
        logger.info("login failed by {}...", uid);
        return null;
    }
    //修改学生密码
    public boolean updatePassword(String studentId, String currentPassword, String newPassword) {
        User user = getUserById(studentId);
        if (user != null && user.getPwd().equals(currentPassword)) {
            int rowsAffected = jdbcTemplate.update("UPDATE users SET pwd = ? WHERE uid = ?", newPassword, studentId);
            if (rowsAffected > 0) {
                return true;
            }
        }
        return false;
    }

    public void updateInfo(User user) {
        String sql = "UPDATE user_info SET name = ?, email = ?, phone = ?, imgurl = ? WHERE uid = ?";
        try {
            jdbcTemplate.update(sql, user.getName(), user.getEmail(),user.getPhone(),user.getImgurl(), user.getUid());
        } catch (Exception e) {
            logger.info("update info failed by {}...", user.getUid());
        }

    }




}
