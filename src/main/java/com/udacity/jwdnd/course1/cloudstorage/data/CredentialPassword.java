package com.udacity.jwdnd.course1.cloudstorage.data;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;

public class CredentialPassword extends Credential {

    private String decodedPassword;

    public String getDecodedPassword() {
        return decodedPassword;
    }

    private EncryptionService encryptionService = new EncryptionService();

    public CredentialPassword(Integer credentialId, String url, String userName, String key, String password, Integer userId) {
        super(credentialId, url, userName, key, password, userId);
        decodedPassword = encryptionService.decryptValue(password,key);
    }
}
