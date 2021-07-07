package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.CredentialPassword;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    /*
    credentialid Integer PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid Integer,
    foreign key (userid) references USERS(userid)
    */

    @Select("SELECT * FROM credentials WHERE credentialId = #{credentialId}")
    Credential findCredentialByCredentialId(Integer credentialId);

    @Select("SELECT * FROM credentials WHERE userid = #{userId}")
    User findCredentialByUserId(Integer userId);

    @Select("SELECT * FROM credentials ORDER BY credentialId")
    List<Credential> findAllCredentials();

    @Select("SELECT * FROM credentials WHERE username = #{userName}")
    User findCredentialByUserName(String userName);

    @Insert("INSERT INTO credentials (url, username, key, password, userid) " +
            "VALUES(#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insertCredential(Credential credential);

    @Update("UPDATE credentials set url=#{url}, key=#{key}, username=#{userName}, password=#{password}" +
             "WHERE credentialid=#{credentialId}")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialId}")
    Integer deleteCredential(Integer credentialId);

}
