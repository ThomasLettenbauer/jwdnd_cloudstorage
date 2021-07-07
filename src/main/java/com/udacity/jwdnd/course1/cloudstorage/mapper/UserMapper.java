package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE userid = #{userId}")
    User findUserById(Integer userId);

    @Select("SELECT * FROM users WHERE username = #{userName}")
    User findUserByName(String userName);

    @Insert("INSERT INTO users (username, salt, password, firstname, lastname) " +
            "VALUES(#{userName}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insertUser(User user);

    @Delete("DELETE FROM users WHERE userid = #{userId}")
    void deleteUser(Integer userId);
}
