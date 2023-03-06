package com.vodafone.learner_academy.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Basic
    @Column(name = "duration", nullable = true)
    private Time duration;
    @Basic
    @Column(name = "take_place", nullable = true)
    private Timestamp takePlace;
    @ManyToOne
    @JoinColumn(name = "teacher", referencedColumnName = "id")
    private User teacher;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
    @ManyToMany()
    @JoinTable(name = "student_course", schema = "learner_academy", inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))

    private Collection<Student> students;

    public Course(String name, Time duration, Timestamp takePlace, User teacher, Room room, Subject subject) {
        this.name = name;
        this.duration = duration;
        this.takePlace = takePlace;
        this.teacher = teacher;
        this.room = room;
        this.subject = subject;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Timestamp getTakePlace() {
        return takePlace;
    }

    public void setTakePlace(Timestamp takePlace) {
        this.takePlace = takePlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (duration != null ? !duration.equals(course.duration) : course.duration != null) return false;
        if (takePlace != null ? !takePlace.equals(course.takePlace) : course.takePlace != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (takePlace != null ? takePlace.hashCode() : 0);
        return result;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Timestamp finishAt(){
        return new Timestamp(takePlace.getTime() + (1000 *duration.getTime()));
    }
}
