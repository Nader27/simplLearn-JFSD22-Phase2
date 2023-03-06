package com.vodafone.learner_academy.dao;

import com.vodafone.learner_academy.model.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class SessionDao extends AbstractDao<Session> {

    public static String SESSION_ID = "session";
    public static String SESSION_SECRET = "secret";

    @Override
    public void seedDB() {
    }

    public String generateRandom() {

        try {
            SecureRandom rand = new SecureRandom();
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256, rand);
            byte[] secretKey = generator.generateKey().getEncoded();
            return Base64.getEncoder().encodeToString(secretKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateAllSessions() {
        List<Session> sessions = this.getAll();
        Instant instant = Instant.now().plus(3, ChronoUnit.HOURS);
        for (Session session : sessions) {
            if (!session.isNeverExpire() && session.getLastUpdate().before(Timestamp.from(instant))) {
                delete(session);
            }
        }
    }

    public Session getSession(String sessionSecret, int sessionId) {
        Optional<Session> session = this.get(sessionId);
        if (session.isPresent()) {
            if (session.get().getSession().equals(sessionSecret))
                session.get().setLastUpdate(Timestamp.from(Instant.now()));
            else throw new RuntimeException("Cookie Theft Error");
        }else return null;
        return session.get();
    }


    public Session getSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute(SessionDao.SESSION_ID) != null &&
                httpSession.getAttribute(SessionDao.SESSION_SECRET) != null) {
            int sessionId = (int) httpSession.getAttribute(SessionDao.SESSION_ID);
            String sessionSecret = String.valueOf(httpSession.getAttribute(SessionDao.SESSION_SECRET));
            Session session = this.get(sessionId).orElseThrow();
            if (session.getSession().equals(sessionSecret))
                session.setLastUpdate(Timestamp.from(Instant.now()));
            else {
                httpSession.invalidate();
                throw new RuntimeException("Cookie Theft Error");
            }
            return session;
        } else return null;
    }
}
