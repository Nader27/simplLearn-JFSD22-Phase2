package com.vodafone.learner_academy.dao;

import com.vodafone.learner_academy.model.UserType;

public class UserTypeDao extends AbstractDao<UserType> {

    @Override
    public void seedDB() {
        UserType adminUserType = new UserType();
        adminUserType.setType("Admin");
        this.save(adminUserType);
        UserType teacherUserType = new UserType();
        adminUserType.setType("Teacher");
        this.save(teacherUserType);
    }
}
