package com.vodafone.learner_academy.dao;

import com.vodafone.learner_academy.model.Student;

public class StudentDao extends AbstractDao<Student> {
    @Override
    protected void seedDB() {
        save(new Student("Hania Begum Adam",2));
        save(new Student("Fathi Hussain Ahmed",1));
        save(new Student("Ismail Ali Hussain",2));
        save(new Student("Usama Adam Abbas",4));
    }
}
