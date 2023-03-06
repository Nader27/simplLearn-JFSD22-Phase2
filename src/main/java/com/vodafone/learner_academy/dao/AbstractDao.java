package com.vodafone.learner_academy.dao;

import com.vodafone.learner_academy.config.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractDao<T> implements Dao<T> {

    private static Session session;
    private final Class<T> persistentClass;

    public AbstractDao() {
        persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        SessionFactory factory = HibernateUtil.getSessionFactory();
        session = factory.openSession();
        if (getAll().isEmpty())
            seedDB();
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = session.getTransaction();
        try {
            tx.begin();
            action.accept(session);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public Optional<T> get(int id) {
        return Optional.ofNullable(session.find(persistentClass, id));
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery =
                criteriaBuilder.createQuery(persistentClass);

        Root<T> root = criteriaQuery.from(persistentClass);
        criteriaQuery.select(root);

        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void save(T t) {
        executeInsideTransaction(session -> session.persist(t));
    }

    @Override
    public void update(T t) {
        executeInsideTransaction(session -> session.merge(t));
    }

    @Override
    public void delete(T t) {
        executeInsideTransaction(session -> session.remove(t));
    }

    protected abstract void seedDB();

}
