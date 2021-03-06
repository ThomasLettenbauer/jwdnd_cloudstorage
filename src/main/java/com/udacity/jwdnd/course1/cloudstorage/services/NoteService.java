package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    final private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Integer addUpdateNote(Note note) {

        if (note.getNoteId() != null) {
            return noteMapper.updateNote(note);
        } else {
            return noteMapper.insertNote(note);
        }
    }

    public Integer deleteNote(Integer noteId, Integer userId) {

        return noteMapper.deleteNote(noteId, userId);

    }

    public List<Note> getAllNotes(Integer userId) {

        return noteMapper.findNotesByUserId(userId);
    }

}
