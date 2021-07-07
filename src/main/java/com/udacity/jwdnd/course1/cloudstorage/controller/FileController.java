package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        String uploadError = null;

        if (fileService.fileExists(fileUpload.getOriginalFilename())) {
            uploadError = "This filename already exists.";
        }

        if (uploadError == null) {

            InputStream fis = fileUpload.getInputStream();

            //File(Integer fileId, String fileName, String contentType, String fileSize, Integer userId, byte[] fileData)
            int rowsAdded = fileService.createFile(new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), String.valueOf(fileUpload.getSize()), userService.getUser(authentication.getName()).getUserId(),
                        fis.readAllBytes()));
            System.out.println("***** File: " + fileUpload.getOriginalFilename());
            if (rowsAdded <= 0) {
                uploadError = "There was an error uploading the file. Please try again.";
            }
        }

        if (uploadError == null) {
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadError", uploadError);
        }

        model.addAttribute("navLink", "/home#nav-files");

        return "result";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer fileId, Authentication authentication)  {

        File file = fileService.getFileById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFileName() + "\"").body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Authentication authentication, Model model) {

        String uploadError = null;
        Integer deletedFileId = fileService.deleteFileById(fileId);

        if (deletedFileId.equals(fileId)) {
            model.addAttribute("uploadSuccess", true);
        } else {
            model.addAttribute("uploadError", "There was an error deleting the file");
        }

        model.addAttribute("navLink", "/home#nav-files");

        return "result";
    }
}
