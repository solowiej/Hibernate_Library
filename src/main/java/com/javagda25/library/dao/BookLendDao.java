package com.javagda25.library.dao;

import com.javagda25.library.model.BookLent;
import com.javagda25.library.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class BookLendDao {
    public List<BookLent> getUnreturnedBookLendsByClient(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookLent> query = cb.createQuery(BookLent.class);
            Root<BookLent> root = query.from(BookLent.class);

            query.select(root).where(
                    cb.and(
                            cb.equal(root.get("client"), clientId),
                            cb.isNull(root.get("dateReturned")))
            );
            return session.createQuery(query).getResultList();
        }
    }

    public List<BookLent> getUnreturnedBookLents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookLent> query = cb.createQuery(BookLent.class);
            Root<BookLent> root = query.from(BookLent.class);

            query.select(root).where(
                    cb.and(
                            cb.isNull(root.get("dateReturned")))
            );
            return session.createQuery(query).getResultList();
        }
    }

    public List<BookLent> getListOfBooksReturnedWithinNHours(LocalDateTime hours) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookLent> query = cb.createQuery(BookLent.class);
            Root<BookLent> root = query.from(BookLent.class);

            query.select(root).where(
                    cb.greaterThan(root.get("dateReturned"), hours)
            );
            return session.createQuery(query).getResultList();
        }
    }

    public List<BookLent> getListOfBooksLendedWithinTheLastDay() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<BookLent> query = cb.createQuery(BookLent.class);
            Root<BookLent> root = query.from(BookLent.class);

            LocalDateTime oneDay = LocalDateTime.now().minusHours(24);

            query.select(root).where(
                    cb.greaterThan(root.get("dateLent"), oneDay)
            );
            return session.createQuery(query).getResultList();
        }
    }

//    public List<BookLent> getlistOfTheMostBorrowedBooks() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<BookLent> query = cb.createQuery(BookLent.class);
//            Root<BookLent> root = query.from(BookLent.class);
//
//
//            query.select(root).where(
//
//                    cb.count()
//
//            );
//            return session.createQuery(query).getResultList();
//        }
//    }

}
