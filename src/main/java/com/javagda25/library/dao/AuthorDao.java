package com.javagda25.library.dao;

import com.javagda25.library.model.Author;
import com.javagda25.library.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AuthorDao {
    public List<Author> getAuthorListBySurname(String surname) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Author> query = cb.createQuery(Author.class);
            Root<Author> root = query.from(Author.class);

            query.select(root).where(cb.like(root.get("surName"), surname));
            return session.createQuery(query).getResultList();
        }
    }
}

