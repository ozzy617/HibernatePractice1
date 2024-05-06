package com.firsthib.hibernateStarter;

import com.firsthib.converter.BirthdayConverter;
import com.firsthib.entity.*;
import com.firsthib.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class HibernateRunner {
   // private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) {
//        Company company = Company.builder()
//                .name("Google")
//                .build();


//            User user = User.builder()
//                    .username("bond9191@mail.ru")
//                    .personalInfo(PersonalInfo.builder()
//                            .firstname("Bond")
//                            .lastname("Vlasov")
//                            .birthDate(new Birthday(LocalDate.of(2000,01,01)))
//                            .build())
//                    .role(Role.ADMIN)
//                    .company()
//                    .build();
           // log.info("User object is transient state: {} ", user);
            try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
                Session session = sessionFactory.openSession()) {
                User user = User.builder()
                        .username("bond9191@mail.ru")
                        .personalInfo(PersonalInfo.builder()
                                .firstname("Bond")
                                .lastname("Vlasov")
                                .birthDate(new Birthday(LocalDate.of(2000,01,01)))
                                .build())
                        .role(Role.ADMIN)
                        .company(session.get(Company.class, 2))
                        .build();
                session.beginTransaction();
                // session.saveOrUpdate(company);
                //session.saveOrUpdate(user);
                User user3 = session.get(User.class,1);
                session.save(user);
                session.getTransaction().commit();

            }
            //session.saveOrUpdate(user);
//
        //}
    }
}
