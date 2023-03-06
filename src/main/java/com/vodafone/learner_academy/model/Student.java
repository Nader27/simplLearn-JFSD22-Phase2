package com.vodafone.learner_academy.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Student {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "full_name", nullable = true, length = 50)
    private String fullName;
    @Basic
    @Column(name = "level", nullable = true)
    private Integer level;
    @ManyToMany(mappedBy = "students")
    private Collection<Course> courses;

    public Student() {
    }

    public Student(String fullName, Integer level) {
        this.fullName = fullName;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (fullName != null ? !fullName.equals(student.fullName) : student.fullName != null) return false;
        if (level != null ? !level.equals(student.level) : student.level != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }

    public Collection<Course> getCourses() {
        return courses;
    }

    public void setCourses(Collection<Course> courses) {
        this.courses = courses;
    }
}
