package com.javagda25.library.dao;

import com.javagda25.library.model.Book;
import com.javagda25.library.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookDao {
    public List<Book> getListofAvailableBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);

            query.select(root).where(
                    cb.and(
                            cb.ge(root.get("numberOfAvaibleCopies"), root.get("numberOfBorrowedCopies")),
                            cb.notEqual(root.get("numberOfAvaibleCopies"), root.get("numberOfBorrowedCopies"))
                    )
            );
            return session.createQuery(query).getResultList();
        }
    }

    public List<Book> getListofUnavailableBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);

            query.select(root).where(
                    cb.equal(root.get("numberOfAvaibleCopies"), root.get("numberOfBorrowedCopies"))
            );
            return session.createQuery(query).getResultList();
        }
    }

}
