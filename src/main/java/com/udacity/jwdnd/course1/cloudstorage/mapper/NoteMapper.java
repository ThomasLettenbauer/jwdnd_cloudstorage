package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    /*
    noteid INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
    */
    
    @Select("SELECT * FROM notes WHERE noteid = #{noteId}")
    Note findNoteByNoteId(int noteId);

    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> findNotesByUserId(String userId);

    @Select("SELECT * FROM notes ORDER BY noteId")
    List<Note> findAllNotes();

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Note note);

    @Update("UPDATE notes set notetitle = #{noteTitle}, notedescription = #{noteDescription} " +
            "WHERE noteid = #{noteId}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM notes WHERE noteid = #{noteId}")
    Integer deleteNote(Integer noteId);


}
