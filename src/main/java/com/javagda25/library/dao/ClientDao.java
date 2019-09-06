package com.javagda25.library.dao;

import com.javagda25.library.model.Client;
import com.javagda25.library.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class ClientDao {
    public List<Client> getClientsByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Client> query = cb.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);

            query.select(root).where(
                    cb.like(root.get("name"), name)
            );
            return session.createQuery(query).getResultList();
        }
    }

    public Optional<Client> getClientByIdNumber(String idNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Client> query = cb.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);

            query.select(root).where(
                    cb.like(root.get("idNumber"), idNumber)
            );
            return Optional.ofNullable(session.createQuery(query).uniqueResult());
        }
    }
}
