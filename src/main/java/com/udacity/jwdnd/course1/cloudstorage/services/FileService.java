package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean fileExists(String fileName) {
        return fileMapper.findFileByFileName(fileName) != null;
    }

    public int createFile(File file) {

        return fileMapper.insertFile(new File(null, file.getFileName(), file.getContentType(), file.getFileSize(), file.getUserId(), file.getFileData()));

    }

    public List<File> getAllFiles () {
        return fileMapper.findAllFiles();
    }

    public File getFileById (Integer fileId) {
        return fileMapper.findFileByFileId(fileId);
    }

    public Integer deleteFileById (Integer fileId) {
        return fileMapper.deleteFileById(fileId);
    }

}
