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
public class AdminService {

    //日志记录
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;

    //单个用户
    RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);
    //查询所有用户
    public List<User> findAllUser() {
        String sql = "SELECT * FROM users,user_info WHERE users.uid = user_info.uid";
        List<User> userList = null;
        try {
            userList = jdbcTemplate.query(sql, userRowMapper);
        } catch (Exception e) {
            logger.info("查询用户信息失败！");
        }
        return userList;
    }

    //根据用户uid删除用户
    public void deleteUser(String uid) {
        try {
            jdbcTemplate.update("DELETE FROM user_info WHERE uid = ?", uid);
            jdbcTemplate.update("DELETE FROM users CASCADED WHERE uid = ?", uid);
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("删除用户失败！");
            return;
        }
        logger.info("删除用户成功！");
    }
    //根据用户uid更新用户信息
    public void updateUser(User user) {
        try {
            jdbcTemplate.update("UPDATE user_info SET name = ?,phone = ?, email = ? WHERE uid = ?",
                    user.getName(), user.getPhone(), user.getEmail(), user.getUid());
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("更新用户信息失败！");
            return;
        }
        logger.info("更新用户信息成功！");
    }
    //增加用户
    public boolean addUser(User user) {
        try {
            jdbcTemplate.update("INSERT INTO users(uid,role,pwd) VALUES(?,?,?)", user.getUid(), user.getRole(), user.getPwd());
            jdbcTemplate.update("INSERT INTO user_info(uid,name,phone,email) VALUES(?,?,?,?)",
                    user.getUid(), user.getName(), user.getPhone(), user.getEmail());
        } catch (Exception e) {
            logger.info(e.getMessage());
            logger.info("增加用户失败！");
            return false;
        }
        logger.info("增加用户成功！");
        return true;
    }
    public void resetPwd(String uid) {
    try {
        String newPwd = "123456";
        jdbcTemplate.update("UPDATE users SET pwd = ? WHERE uid = ?", newPwd, uid);
    }
    catch (Exception e) {
        logger.info(e.getMessage());
        logger.info("重置密码失败！");}
    logger.info("重置密码成功！");
    }



}
