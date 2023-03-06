package com.vodafone.learner_academy.model;

import com.vodafone.learner_academy.dao.SessionDao;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class Session {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "session", length = 50, nullable = true)
    private String session;
    @Basic
    @Column(name = "last_update", nullable = true)
    private Timestamp lastUpdate;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;
    @Basic
    @Column(name = "neverExpire", nullable = false)
    private boolean neverExpire;

    public Session() {

    }

    public Session(User user,boolean neverExpire) {
        this.user = user;
        this.session = new SessionDao().generateRandom();
        this.lastUpdate =  Timestamp.from(Instant.now());
        this.neverExpire = neverExpire;
    }

    public Session(User user) {
        this.user = user;
        this.session = new SessionDao().generateRandom();
        this.lastUpdate =  Timestamp.from(Instant.now());
        this.neverExpire = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session1 = (Session) o;

        if (id != session1.id) return false;
        if (session != null ? !session.equals(session1.session) : session1.session != null) return false;
        if (lastUpdate != null ? !lastUpdate.equals(session1.lastUpdate) : session1.lastUpdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isNeverExpire() {
        return neverExpire;
    }

    public void setNeverExpire(boolean neverExpire) {
        this.neverExpire = neverExpire;
    }
}
