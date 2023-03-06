package com.vodafone.learner_academy.dao;

import com.vodafone.learner_academy.model.Subject;

public class SubjectDao extends AbstractDao<Subject> {
    @Override
    protected void seedDB() {
        save(new Subject("Math"));
        save(new Subject("Science"));
        save(new Subject("English"));
    }
}
