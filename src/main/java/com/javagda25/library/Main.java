package com.javagda25.library;

import com.javagda25.library.dao.*;
import com.javagda25.library.model.Author;
import com.javagda25.library.model.Book;
import com.javagda25.library.model.BookLent;
import com.javagda25.library.model.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityDao dao = new EntityDao();
        AuthorDao authorDao = new AuthorDao();
        ClientDao clientDao = new ClientDao();
        BookLendDao bookLendDao = new BookLendDao();
        BookDao bookDao = new BookDao();

        System.out.println("*******Author*******\n" +
                "* Add Author [aa]\n" +
                "* List Authors [la]\n" +
                "* Edit Author [ea]\n" +
                "* Delete Author [da]\n" +
                "\n" +
                "*******Book*******\n" +
                "* Add Book [ab]\n" +
                "* List Books [lb]\n" +
                "* Edit Book [eb]\n" +
                "* Delete Book [db]\n" +
                " \n" +
                "*******Client*******\n" +
                "* Add Client [ac]\n" +
                "* List Clients [lc]\n" +
                "* Edit Client [ec]\n" +
                "* Delete Client [dc]\n" +
                "\n" +
                "*******COMMAND********\n" +
                "* Bind Author with Book [bab]\n" +
                "* Lend Book [lnb]\n" +
                "* Return Book [rb]\n" +
                "* List Authors by Surname [las]\n" +
                "* List Clients by Name [lcn]\n" +
                "* Get Clients by idNumber [gci]\n" +
                "* List of Books lended by Client [lbc]\n" +
                "* List of Client's unreturned Books [lub]\n" +
                "* List of available Books [lav]\n" +
                "* List of unavailable Books [luv]\n" +
                "* List of unreturned Books [lur]\n" +
                "* List of Books returned within N hours [lnh]\n" +
                "* List of Books lended within the last 24 hours [lnd]\n" +
                "* List of the most borrowed books [lmb]\n" +
                "* Get the most active Client [mac]\n");

        String line;
        do {

            line = scanner.nextLine();

            if (line.equalsIgnoreCase("aa")) {
                addAuthor(dao);

            } else if (line.equalsIgnoreCase("la")) {
                dao.getAll(Author.class).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("ea")) {
                updateAuthor(dao);

            } else if (line.equalsIgnoreCase("da")) {
                dao.delete(Author.class, scanner.nextLong());

            } else if (line.equalsIgnoreCase("ab")) {
                addBook(dao);

            } else if (line.equalsIgnoreCase("lb")) {
                dao.getAll(Book.class).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("eb")) {
                updateBook(dao);

            } else if (line.equalsIgnoreCase("db")) {
                dao.delete(Book.class, scanner.nextLong());

            } else if (line.equalsIgnoreCase("ac")) {
                addClient(dao);

            } else if (line.equalsIgnoreCase("lc")) {
                dao.getAll(Client.class).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("ec")) {
                updateClient(dao);

            } else if (line.equalsIgnoreCase("dc")) {
                dao.delete(Client.class, scanner.nextLong());

            } else if (line.equalsIgnoreCase("bab")) {
                bindAuthorWithBook(dao);

            } else if (line.equalsIgnoreCase("lnb")) {
                lendBook(dao);

            } else if (line.equalsIgnoreCase("rb")) {
                returnTheBook(dao);

            } else if (line.equalsIgnoreCase("las")) {
                System.out.println("Enter Author's Surname:");
                authorDao.getAuthorsListBySurname(scanner.nextLine()).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("lcn")) {
                System.out.println("Enter Client's Name:");
                clientDao.getClientsByName(scanner.nextLine()).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("gci")) {
                System.out.println("Enter Client's idNumber:");
                System.out.println(clientDao.getClientByIdNumber(scanner.nextLine()));

            } else if (line.equalsIgnoreCase("lbc")) {
                System.out.println("Client id:");
                Optional<Client> optionalClient = dao.getById(Client.class, scanner.nextLong());
                optionalClient.ifPresent(client -> client.getLentsBooks()
                        .stream()
                        .map(BookLent::getBook)
                        .forEach(System.out::print));

            } else if (line.equalsIgnoreCase("lub")) {
                System.out.println("Client id:");
                Long clientId = scanner.nextLong();

                Optional<Client> optionalClient = dao.getById(Client.class, clientId);
                if (optionalClient.isPresent()) {
                    bookLendDao.getUnreturnedBookLendsByClient(clientId).forEach(System.out::println);
                }

            } else if (line.equalsIgnoreCase("lav")) {
                bookDao.getListofAvailableBooks().forEach(System.out::println);

            } else if (line.equalsIgnoreCase("luv")) {
                bookDao.getListofUnavailableBooks().forEach(System.out::println);

            } else if (line.equalsIgnoreCase("lur")) {
                bookLendDao.getUnreturnedBookLents().forEach(System.out::println);

            } else if (line.equalsIgnoreCase("lnh")) {
                System.out.println("Enter the number of hours:");
                LocalDateTime hours = LocalDateTime.now().minusHours(scanner.nextLong());
                bookLendDao.getListOfBooksReturnedWithinNHours(hours).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("lnd")) {
                bookLendDao.getListOfBooksLendedWithinTheLastDay().forEach(System.out::println);

            } else if (line.equalsIgnoreCase("lmb")) {
                bookLendDao.getlistOfTheMostBorrowedBooks().forEach(System.out::println);

            } else if (line.equalsIgnoreCase("mac")) {
                System.out.println(bookLendDao.getTheMostActiveClient());

            }
        } while (!line.equalsIgnoreCase("quit"));
    }

    private static void addAuthor(EntityDao dao) {
        Author author = new Author();

        System.out.println("Podaj imie:");
        author.setName(scanner.nextLine());
        System.out.println("Podaj nazwisko:");
        author.setSurName(scanner.nextLine());
        System.out.println("Podaj date urodzenia:");
        author.setDateOfBirth(LocalDate.parse(scanner.nextLine()));

        dao.saveOrUpdate(author);
    }

    private static void addBook(EntityDao dao) {
        Book book = new Book();

        System.out.println("Nazwa ksiazki:");
        book.setTitle(scanner.nextLine());
        System.out.println("Data wydania:");
        book.setYearWritten(LocalDate.parse(scanner.nextLine()));
        System.out.println("Ilosc stron:");
        book.setNumberOfPages(scanner.nextInt());
        System.out.println("Dostepnych kopi:");
        book.setNumberOfAvaibleCopies(scanner.nextInt());

        dao.saveOrUpdate(book);
    }

    private static void addClient(EntityDao dao) {
        Client client = new Client();

        System.out.println("Imie:");
        client.setName(scanner.nextLine());
        System.out.println("Nazwisko:");
        client.setSurName(scanner.nextLine());
        System.out.println("Nr dowodu osobistego:");
        client.setIdNumber(scanner.nextLine());

        dao.saveOrUpdate(client);
    }

    private static void updateAuthor(EntityDao dao) {
        System.out.println("Id autora:");

        Optional<Author> optionalAuthor = dao.getById(Author.class, scanner.nextLong());

        if (optionalAuthor.isPresent()) {
            System.out.println("Co chcesz zmienic: [imie [i]/ nazwisko [n]/ data urodzenia [du] / wyjdz [q]]");

            String line;
            do {
                line = scanner.nextLine();

                if (line.equalsIgnoreCase("i")) {
                    System.out.println("Podaj imie:");
                    optionalAuthor.get().setName(scanner.nextLine());
                    dao.saveOrUpdate(optionalAuthor.get());


                } else if (line.equalsIgnoreCase("n")) {
                    System.out.println("Podaj nazwisko:");

                    optionalAuthor.get().setSurName(scanner.nextLine());
                    dao.saveOrUpdate(optionalAuthor.get());

                } else if (line.equalsIgnoreCase("du")) {
                    System.out.println("Podaj date urodzenia:");
                    optionalAuthor.get().setDateOfBirth(LocalDate.parse(scanner.nextLine()));
                    dao.saveOrUpdate(optionalAuthor.get());
                }

            } while (!line.equalsIgnoreCase("q"));
        }
    }

    private static void updateBook(EntityDao dao) {
        System.out.println("Id ksiazki:");
        Optional<Book> optionalClient = dao.getById(Book.class, scanner.nextLong());

        if (optionalClient.isPresent()) {
            System.out.println("Co chcesz zmienic: [nazwa [n]/ data wydania [d]/ ilosc stron [s] / dostepnych kopii [k] / wyjdz [q]]");

            String line;
            do {
                line = scanner.nextLine();

                if (line.equalsIgnoreCase("n")) {
                    System.out.println("Nazwa:");
                    optionalClient.get().setTitle(scanner.nextLine());
                    dao.saveOrUpdate(optionalClient.get());

                } else if (line.equalsIgnoreCase("d")) {
                    System.out.println("Data wydania:");
                    optionalClient.get().setYearWritten(LocalDate.parse(scanner.nextLine()));
                    dao.saveOrUpdate(optionalClient.get());

                } else if (line.equalsIgnoreCase("s")) {
                    System.out.println("Ilosc stron:");
                    optionalClient.get().setNumberOfPages(scanner.nextInt());
                    dao.saveOrUpdate(optionalClient.get());

                } else if (line.equalsIgnoreCase("k")) {
                    System.out.println("Dostepnych kopii:");
                    optionalClient.get().setNumberOfAvaibleCopies(scanner.nextInt());
                    dao.saveOrUpdate(optionalClient.get());
                }
            } while (!line.equalsIgnoreCase("q"));
        }
    }

    private static void updateClient(EntityDao dao) {
        System.out.println("Id klienta:");
        Optional<Client> optionalClient = dao.getById(Client.class, scanner.nextLong());

        if (optionalClient.isPresent()) {
            System.out.println("Co chcesz zmienic: [imie [i]/ nazwisko [n]/ nr dowodu osobistego [d] / wyjdz [q]]");

            String line;
            do {
                line = scanner.nextLine();

                if (line.equalsIgnoreCase("i")) {
                    System.out.println("Imie:");
                    optionalClient.get().setName(scanner.nextLine());
                    dao.saveOrUpdate(optionalClient.get());

                } else if (line.equalsIgnoreCase("n")) {
                    System.out.println("Nazwisko:");
                    optionalClient.get().setSurName(scanner.nextLine());
                    dao.saveOrUpdate(optionalClient.get());

                } else if (line.equalsIgnoreCase("d")) {
                    System.out.println("Nr dowodu osobistego:");
                    optionalClient.get().setIdNumber(scanner.nextLine());
                    dao.saveOrUpdate(optionalClient.get());
                }
            } while (!line.equalsIgnoreCase("q"));
        }
    }

    private static void bindAuthorWithBook(EntityDao dao) {
        System.out.println("Book id:");
        Long bookId = scanner.nextLong();
        Optional<Book> optionalBook = dao.getById(Book.class, bookId);

        if (optionalBook.isPresent()) {
            System.out.println("Author id:");
            Long authorId = scanner.nextLong();

            Optional<Author> optionalAuthor = dao.getById(Author.class, authorId);

            if (optionalAuthor.isPresent()) {

                Author author = optionalAuthor.get();
                Book book = optionalBook.get();

                dao.saveOrUpdate(book);
                author.getBooks().add(book);
                dao.saveOrUpdate(author);

            } else {
                System.err.println("There is no such Author with id = " + authorId + " in Database.");
            }
        } else {
            System.err.println("There is no such Book with id = " + bookId + " in Database.");
        }
    }

    private static void lendBook(EntityDao dao) {
        BookLent bookLent = new BookLent();

        System.out.println("Client id:");
        Optional<Client> optionalClient = dao.getById(Client.class, scanner.nextLong());
        System.out.println("Book id:");
        Optional<Book> optionalBook = dao.getById(Book.class, scanner.nextLong());

        if (optionalClient.isPresent() && optionalBook.isPresent()) {

            if (optionalBook.get().getNumberOfAvaibleCopies() > optionalBook.get().getNumberOfBorrowedCopies()) {
                Client client = optionalClient.get();
                Book book = optionalBook.get();
                bookLent.setDateLent(LocalDateTime.now());
                bookLent.setBook(book);
                bookLent.setClient(client);
                dao.saveOrUpdate(bookLent);

            } else {
                System.err.println("All books are temporarily on loan.");
            }
        } else {
            System.err.println("There is no such element in Database.");
        }
    }

    private static void returnTheBook(EntityDao dao) {
        BookLent bookLent = new BookLent();

        System.out.println("Enter Client id:");
        Long clientId = scanner.nextLong();
        Optional<Client> optionalClient = dao.getById(Client.class, clientId);

        if (optionalClient.isPresent()) {
            System.out.println("Borowed Books:");
            optionalClient.get().getLentsBooks().forEach(System.out::println);
            System.out.println("Book id:");
            Long bookLendId = scanner.nextLong();

            Optional<BookLent> optionalBookLent = dao.getById(BookLent.class, bookLendId);
            if (optionalBookLent.isPresent()) {
                bookLent.setId(bookLendId);
                bookLent.setDateLent(optionalBookLent.get().getDateLent());
                bookLent.setDateReturned(LocalDateTime.now());
                bookLent.setBook(optionalBookLent.get().getBook());
                bookLent.setClient(optionalBookLent.get().getClient());

                dao.saveOrUpdate(bookLent);
            } else {
                System.err.println("The client with given " + clientId +
                        " , has no assigned bookLend with id = " + bookLendId);
            }
        } else {
            System.err.println("There is no such Client with id = " + clientId + " in Database.");
        }
    }
}
