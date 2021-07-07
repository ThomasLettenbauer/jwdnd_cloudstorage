package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    final private CredentialService credentialService;
    final private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credential/addupdate")
    public String addCredential(@ModelAttribute Credential credential, Model model, Authentication authentication) {

        User user = userService.getUser(authentication.getName());

        credential.setUserId(user.getUserId());

        if ( credentialService.addUpdateCredential(credential) > 0 ) {
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadError", "There was an error saving the credential");
        }

        model.addAttribute("navLink", "/home#nav-credentials");

        return "result";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, @ModelAttribute Credential credential, Model model) {

        if ( credentialService.deleteCredential(credentialId) > 0 ) {
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadError", "There was an error deleting the credential");
        }

        model.addAttribute("navLink", "/home#nav-credentials");

        return "result";
    }
}
