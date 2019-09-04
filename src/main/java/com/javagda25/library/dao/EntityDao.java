package com.javagda25.library.dao;


import com.javagda25.library.util.HibernateUtil;
import com.javagda25.library.model.IBaseEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityDao {

    public <T extends IBaseEntity> void saveOrUpdate(T entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(entity);

            transaction.commit();

        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public <T extends IBaseEntity> List<T> getAll(Class<T> tClassT) {
        List<T> list = new ArrayList<>();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = cb.createQuery(tClassT);
            Root<T> rootTable = criteriaQuery.from(tClassT);
            criteriaQuery.select(rootTable);

            list.addAll(session.createQuery(criteriaQuery).list());

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }


    public <T extends IBaseEntity> Optional<T> getById(Class<T> classT, Long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {

            T entity = session.get(classT, id);
            return Optional.ofNullable(entity);
        }
    }

    public <T extends IBaseEntity> void delete(Class<T> classT, Long id) {
        Optional<T> optionalEntity = getById(classT, id);

        if (optionalEntity.isPresent()) {
            delete(optionalEntity.get());
        } else {
            System.err.println("Nie udalo sie znalezc instancji");
        }

    }

    public <T extends IBaseEntity> void delete(T entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.delete(entity);

            transaction.commit();
        }
    }
}
