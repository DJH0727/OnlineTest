package org.web.onlinetest;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        String CreateUserTableSql = """
                CREATE TABLE IF NOT EXISTS Users (
                    UserID BIGINT,
                    Username VARCHAR(50) NOT NULL,
                    Password VARCHAR(255) NOT NULL,
                    UserType ENUM('Admin', 'User') NOT NULL,
                    Phone VARCHAR(20) ,
                    Email VARCHAR(50)  ,
                    PRIMARY KEY (UserID)
                );""";


        //jdbcTemplate.update(CreateUserTableSql);
        //jdbcTemplate.update("INSERT INTO Users (UserID, Username, Password, UserType) VALUES ('37220222203581', 'djh', 'djh666', 'Admin')");
    }

}
