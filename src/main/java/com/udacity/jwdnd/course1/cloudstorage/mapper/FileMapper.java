package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    /*
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    filedata BLOB,
    foreign key (userid) references USERS(userid)
    */

    @Select("SELECT * FROM files WHERE fileid = #{fileId}")
    File findFileByFileId(Integer fileId);

    @Select("SELECT * FROM files WHERE filename = #{fileName}")
    File findFileByFileName(String fileName);

    @Select("SELECT * FROM files WHERE userid = #{userId}")
    List<File> findFilesByUserId(Integer userId);

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Delete("DELETE FROM files WHERE fileid = #{fileId} AND userid = #{userId}")
    Integer deleteFileById(Integer fileId, Integer userId);

}
