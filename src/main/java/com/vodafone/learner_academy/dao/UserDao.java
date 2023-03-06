package com.vodafone.learner_academy.dao;

import com.vodafone.learner_academy.model.User;
import com.vodafone.learner_academy.model.UserType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserDao extends AbstractDao<User> {

    @Override
    public void seedDB() {
        //Add Admin User
        byte[] bytesOfMessage = "12345678".getBytes(StandardCharsets.UTF_8);
        String encryptedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] theMD5digest = md.digest(bytesOfMessage);
            encryptedPassword = Base64.getEncoder().encodeToString(theMD5digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        UserType userType = new UserTypeDao().getAll().stream()
                .filter(u -> u.getType().equals(UserType.ADMIN))
                .findFirst().orElseThrow();

        User adminUser = new User("Nader", "Labib", "naderalaa27@gmail.com", encryptedPassword, userType);
        this.save(adminUser);

        //Add Teacher User
        userType = new UserTypeDao().getAll().stream()
                .filter(u -> u.getType().equals(UserType.TEACHER))
                .findFirst().orElseThrow();

        User teacherUser = new User("Ahmed", "Ali", "ahmed.ali@fcih.edu", encryptedPassword, userType);
        this.save(teacherUser);
    }

    public String encryptPassword(String password) {
        try {
            byte[] bytesOfMessage = password.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] theMD5digest = md.digest(bytesOfMessage);
            return Base64.getEncoder().encodeToString(theMD5digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}