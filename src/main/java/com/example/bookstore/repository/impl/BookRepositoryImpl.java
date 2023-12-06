package com.example.bookstore.repository.impl;

import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory factory;

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add a new book: " + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (Session session = factory.openSession()) {
            Query<Book> books = session.createQuery("FROM Book b", Book.class);
            return books.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't find all books.", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Session session = factory.openSession()) {
            Book book = session.find(Book.class, id);
            return Optional.ofNullable(book);
        } catch (Exception e) {
            throw new RuntimeException("Cant find book by id: " + id, e);
        }
    }
}
