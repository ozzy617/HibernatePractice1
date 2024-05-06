package com.firsthib.hibernateStarter;

import com.firsthib.entity.*;
import com.firsthib.util.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class HibernateRunnerTest {
    @Test
    public void chekManyToManyRefactor() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Chat chat = session.get(Chat.class, 1L);
        User user = session.get(User.class, 1L);
        UserChat userChat = UserChat.builder()
                .createdAt(Instant.now())
                .createdBy("Vanya")
                .build();
        userChat.setChat(chat);
        userChat.setUser(user);
        session.getTransaction().commit();

        session.save(userChat);
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
                .username("dimka77777@gmail.su")
                .build();

        Profile profile = Profile.builder()
                .street("Pobedy 9")
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
    @Test
    public void addNewUserAndCompany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = Company.builder()
                .name("Microsoft")
                .build();


        User user = User.builder()
                .username("james@mail.ru")
                .build();

        company.addUser(user);
        session.save(company);
        session.getTransaction().commit();
    }
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