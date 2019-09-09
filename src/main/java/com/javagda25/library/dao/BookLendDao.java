package com.javagda25.library.dao;

import com.javagda25.library.model.Book;
import com.javagda25.library.model.BookLent;
import com.javagda25.library.model.Client;
import com.javagda25.library.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class BookLendDao {
    public List<Book> getUnreturnedBookLendsByClient(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<BookLent> root = query.from(BookLent.class);

            query.select(root.get("book")).where(
                    cb.and(
                            cb.equal(root.get("client"), clientId),
                            cb.isNull(root.get("dateReturned")))
            );
            return session.createQuery(query).getResultList();
        }
    }

    public List<Book> getUnreturnedBookLents() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<BookLent> root = query.from(BookLent.class);

            query.select(root.get("book")).where(
                    cb.and(
                            cb.isNull(root.get("dateReturned")))
            );
            return session.createQuery(query).getResultList();
        }
    }

    public List<Book> getListOfBooksReturnedWithinNHours(LocalDateTime hours) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<BookLent> root = query.from(BookLent.class);

            query.select(root.get("book")).where(
                    cb.greaterThan(root.get("dateReturned"), hours)
            );
            return session.createQuery(query).getResultList();
        }
    }

    public List<Book> getListOfBooksLendedWithinTheLastDay() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<BookLent> root = query.from(BookLent.class);

            LocalDateTime oneDay = LocalDateTime.now().minusHours(24);

            query.select(root.get("book")).where(
                    cb.greaterThan(root.get("dateLent"), oneDay)
            );
            return session.createQuery(query).getResultList();
        }
    }

    // przy kompozycji!!
    public List<Book> getlistOfTheMostBorrowedBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<BookLent> root = query.from(BookLent.class);

            query.select(root.get("book"))
                    .groupBy(root.get("book"))
                    .orderBy(cb.desc(cb.count(root.get("book"))));

            return session.createQuery(query).getResultList();
        }
    }
    public Optional<Client> getTheMostActiveClient() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Client> query = cb.createQuery(Client.class);
            Root<BookLent> root = query.from(BookLent.class);

            query.select(root.get("client"))
                    .groupBy(root.get("client"))
                    .orderBy(cb.desc(cb.count(root.get("book"))));

            return Optional.ofNullable(session.createQuery(query).setMaxResults(1).uniqueResult());
        }
    }

}
