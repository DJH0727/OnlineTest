package org.web.onlinetest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StudentService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    JdbcTemplate jdbcTemplate;


}
