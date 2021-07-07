package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    final private NoteService noteService;
    final private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note/addupdate")
    public String addNote(@ModelAttribute Note note, Model model, Authentication authentication) {

        String uploadError = null;

        User user = userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());

        if ( noteService.addUpdateNote(note) > 0 ) {
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadError", "There was an error saving the note");
        }

        model.addAttribute("navLink", "/home#nav-notes");

        return "result";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, @ModelAttribute Note note, Model model) {

        String uploadError = null;

        if ( noteService.deleteNote(noteId) > 0 ) {
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadError", "There was an error deleting the note");
        }

        model.addAttribute("navLink", "/home#nav-notes");

        return "result";
    }

}
