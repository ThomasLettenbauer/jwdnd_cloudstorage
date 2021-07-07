package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.CredentialPassword;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    final private CredentialMapper credentialMapper;
    final private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Integer addUpdateCredential(Credential credential) {

        if (credential.getCredentialId() != null) {

            String encodedkey = credentialMapper.findCredentialByCredentialId(credential.getCredentialId()).getKey();
            String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedkey);

            credential.setKey(encodedkey);
            credential.setPassword(encodedPassword);

            return credentialMapper.updateCredential(credential);

        } else {

            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedkey = Base64.getEncoder().encodeToString(key);

            String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedkey);

            credential.setKey(encodedkey);
            credential.setPassword(encodedPassword);

            return credentialMapper.insertCredential(credential);
        }
    }

    public Integer deleteCredential(Integer credentialId) {

        return credentialMapper.deleteCredential(credentialId);

    }

    public List<CredentialPassword> getAllCredentials(Integer userId) {

        List<CredentialPassword> credentialPasswordList = new ArrayList<>();

        List<Credential> credentialList = credentialMapper.findCredentialsByUserId(userId);

        for (Credential credential : credentialList) {
            credentialPasswordList.add(new CredentialPassword(credential.getCredentialId(), credential.getUrl(), credential.getUserName(), credential.getKey(),
                    credential.getPassword(), credential.getUserId()));
        }

        return credentialPasswordList;
    }
}
