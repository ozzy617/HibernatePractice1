package com.firsthib.hibernateStarter;

import com.firsthib.dao.UserDao;
import com.firsthib.dto.PaymentFilter;
import com.firsthib.entity.*;
import com.firsthib.util.HibernateUtil;
import com.querydsl.core.Tuple;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class HibernateRunnerTest {
    @Test
    public void chekIsItPossible() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserDao userDao = UserDao.getInstance();
        List<Tuple> res = userDao.isItPossible(session);
        System.out.println(res);

        session.getTransaction().commit();
    }
    @Test
    public void chekfindCompanyNamesWithAvgUserPaymentsOrderedByCompanyName() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserDao userDao = UserDao.getInstance();
        List<Tuple> res = userDao.findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(session);
        System.out.println(res);

        session.getTransaction().commit();
    }
    @Test
    public void chekFindAveragePaymentAmountByFirstAndLastNames() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserDao userDao = UserDao.getInstance();
        Double payment = userDao.findAveragePaymentAmountByFirstAndLastNames(session,
                PaymentFilter.builder().firstname("Andrei").lastname("Ivanov").build());
        System.out.println(payment);

        session.getTransaction().commit();
    }
    @Test
    public void chekFindAllPaymentsByCompanyName() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserDao userDao = UserDao.getInstance();
        List<Payment> payments = userDao.findAllPaymentsByCompanyName(session, "Yandex");
        System.out.println(payments);

        session.getTransaction().commit();
    }
    @Test
    public void chekfindAllByCompanyName() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserDao userDao = UserDao.getInstance();
        List<User> users = userDao.findAllByCompanyName(session, "Google");
        System.out.println(users);

        session.getTransaction().commit();
    }
    @Test
    public void chekFindLimitedUsersOrderedByBirthday() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserDao userDao = UserDao.getInstance();
        List<User> users = userDao.findLimitedUsersOrderedByBirthday(session, 3);
        System.out.println(users);

        session.getTransaction().commit();
    }
    @Test
    public void chekFindAllByName() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserDao userDao = UserDao.getInstance();
        List<User> users = userDao.findAllByFirstName(session, "Pavel");
        System.out.println(users);

        session.getTransaction().commit();
    }
    @Test
    public void chekFindAll() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        UserDao userDao = UserDao.getInstance();
        List<User> users = userDao.findAll(session);
        System.out.println(users);

        session.getTransaction().commit();
    }
    @Test
    public void chekHQL() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        String name = "Pavel";
//        List<User> users = session.createQuery("""
//            select u from User u
//            left join u.company c
//            where u.personalInfo.firstname = :firstname
//            and c.name = :company
//            """).setParameter("firstname", name)
//                .setParameter("company", "Yandex")
//                .list();
//        List<User> users = session.createNamedQuery("findUserByNameAndCompany")
//                .setParameter("firstname", name)
//                .setParameter("company", "Yandex")
//                .list();
        session.createQuery("update User u set role = 'ADMIN'").executeUpdate();
        //System.out.println(users);

        session.getTransaction().commit();
    }
    @Test
    public void chekInheritance() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

//        Company company = Company.builder()
//                .name("Google")
//                .build();
//        session.save(company);
//
//        Programmer programmer = Programmer.builder()
//                .username("ivan@gmail.com")
//                .language(Language.JAVA)
//                .company(company)
//                .build();
//
//        session.save(programmer);
//
//        Manager manager = Manager.builder()
//                .username("pert@gmail.com")
//                .project("Java Core")
//                .company(company)
//                .build();
//
//        session.save(manager);
//        session.flush();
//        session.clear();
//
//        Programmer programmer1 = session.get(Programmer.class, 1L);
//        User manager1 = session.get(User.class, 2L);

        session.getTransaction().commit();
    }
    @Test
    public void chekH2() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = Company.builder()
                .name("Google")
                .build();

        session.getTransaction().commit();
    }
    @Test
    public void chekManyToManyRefactor() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Chat chat = session.get(Chat.class, 1L);
        Chat chat1 = new Chat();
        chat1.setName("Habr");
        User user = session.get(User.class, 1L);
//        UserChat userChat = UserChat.builder()
//                .createdAt(Instant.now())
//                .createdBy("Vanya")
//                .build();
        UserChat userChat = new UserChat();
        userChat.setCreatedAt(Instant.now());
        userChat.setCreatedBy("Vitya");
        userChat.setChat(chat1);
        userChat.setUser(user);

        session.save(userChat);

        session.getTransaction().commit();
    }
    @Test
    public void chekManyToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Chat chat = Chat.builder()
                .name("habr")
                .build();
        User user = session.get(User.class, 3L);
        //user.addChat(chat);
        session.save(chat);

        session.getTransaction().commit();
    }
    @Test
    public void chekOneToOne() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = User.builder()
                .username("pert77777@gmail.su")
                .build();

        Profile profile = Profile.builder()
                .street("Pobedy 2")
                .language("EN")
                .build();
        session.save(user);
        profile.setUser(user);

        session.getTransaction().commit();
    }

    @Test
    public void chekOrphalRemoval() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 2);
        company.getUsers().removeIf(user -> user.getId().equals(7));

        session.getTransaction().commit();
    }
//    @Test
//    public void addNewUserAndCompany() {
//        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        Company company = Company.builder()
//                .name("Microsoft")
//                .build();
//
//
//        User user = User.builder()
//                .username("james@mail.ru")
//                .build();
//
//        company.addUser(user);
//        session.save(company);
//        session.getTransaction().commit();
//    }
    @Test
    public void chekOneToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 2);
        System.out.println(company.getUsers());

        session.getTransaction().commit();
    }
//    @Test
//    public void testHibernateApi() throws SQLException, IllegalAccessException {
//        User user = User.builder()
//                .username("ivan@mail.ru")
//                .firstname("Ivan")
//                .lastname("Ivanov")
//                .birthDate(LocalDate.of(2000,01,01))
//                .age(23)
//                .build();
//        String sql = """
//                insert into
//                %s
//                (%s)
//                values (
//                %s)
//                """;
//        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
//                .map(table -> table.name())
//                .orElse(user.getClass().getName());
//
//        Field[] fields = user.getClass().getDeclaredFields();
//        String columnNames = Arrays.stream(fields)
//                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name).orElse(field.getName())
//                ).collect(Collectors.joining(", "));
//        var columnValues = Arrays.stream(fields)
//                .map(filed -> "?")
//                .collect(Collectors.joining(", "));
//
//        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hib",
//                "postgres", "");
//        PreparedStatement preparedStatement = connection.
//                prepareStatement(sql.formatted(tableName, columnNames, columnValues));
//        for (int i = 0; i < fields.length; i++) {
//            fields[i].setAccessible(true);
//            preparedStatement.setObject(i + 1, fields[i].get(user));
//        }
//        System.out.println(preparedStatement);
//        preparedStatement.executeUpdate();
//
//        preparedStatement.close();
//        connection.close();
//    }

}