package com.javagda25.library;

import com.javagda25.library.dao.EntityDao;
import com.javagda25.library.model.Author;
import com.javagda25.library.model.Book;
import com.javagda25.library.model.Client;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        EntityDao dao = new EntityDao();

        System.out.println("*******Autor*******\n" +
                "* Dodaj Autora [da]\n" +
                "* Pokaz Autorow [pa]\n" +
                "* Edytuj Autora [ea]\n" +
                "* Usun Autora [ua]\n" +
                "\n" +
                "*******Ksiazka*******\n" +
                "* Dodaj Ksiazke [dks]\n" +
                "* Pokaz Ksiazki [pks]\n" +
                "* Edytuj Ksiazke [eks]\n" +
                "* Usun Ksiazke [uks]\n" +
                " \n" +
                "*******Klient*******\n" +
                "* Dodaj Klienta [dkl]\n" +
                "* Pokaz Klientow [pkl]\n" +
                "* Edytuj Klienta [ekl]\n" +
                "* Usun Klienta [ukl]\n");


        String line;
        do {

            line = scanner.nextLine();

            if (line.equalsIgnoreCase("da")) {
                addAuthor(dao);

            } else if (line.equalsIgnoreCase("pa")) {
                dao.getAll(Author.class).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("ea")) {
                updateAuthor(dao);

            } else if (line.equalsIgnoreCase("ua")) {
                dao.delete(Author.class, scanner.nextLong());

            } else if (line.equalsIgnoreCase("dks")) {
                addBook(dao);

            } else if (line.equalsIgnoreCase("pks")) {
                dao.getAll(Book.class).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("eks")) {
                updateBook(dao);

            } else if (line.equalsIgnoreCase("uks")) {
                dao.delete(Book.class, scanner.nextLong());

            } else if (line.equalsIgnoreCase("dkl")) {
                addClient(dao);

            } else if (line.equalsIgnoreCase("pkl")) {
                dao.getAll(Client.class).forEach(System.out::println);

            } else if (line.equalsIgnoreCase("ekl")) {
                updateClient(dao);

            } else if (line.equalsIgnoreCase("ukl")) {
                dao.delete(Client.class, scanner.nextLong());
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

}
