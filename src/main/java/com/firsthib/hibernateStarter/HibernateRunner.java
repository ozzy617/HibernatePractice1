package com.firsthib.hibernateStarter;

import com.firsthib.converter.BirthdayConverter;
import com.firsthib.dao.CompanyRepository;
import com.firsthib.dao.PaymentRepository;
import com.firsthib.dao.UserRepository;
import com.firsthib.dto.UserCreateDto;
import com.firsthib.entity.*;
import com.firsthib.mapper.CompanyReadMapper;
import com.firsthib.mapper.UserCreateMapper;
import com.firsthib.mapper.UserReadMapper;
import com.firsthib.service.UserService;
import com.firsthib.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.graph.GraphSemantic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.LockModeType;
import java.lang.reflect.Proxy;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HibernateRunner {
   // private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) {
//        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//        Session session = sessionFactory.openSession()) {
//            session.beginTransaction();

//            User user = session.get(User.class, 1);
//            System.out.println(user.getCompany());

//            List<User> users = session.createQuery("select u from User u join fetch u.payment", User.class).list();
//            for (User user : users) {
//                System.out.println(user.getPayment());
//            }

//            session.enableFetchProfile("withCompany");
//            var user = session.get(User.class, 1);
//            System.out.println(user.getCompany().getName());

//            var userGraph = session.createEntityGraph(User.class);
//            userGraph.addAttributeNodes("company", "userChats");
//            var userChatsSubraph = userGraph.addSubgraph("userChats", UserChat.class);
//            userChatsSubraph.addAttributeNodes("chat");
//
//            Map<String, Object> properties = Map.of(
//                    GraphSemantic.LOAD.getJpaHintName(), userGraph
//            );
//            var user = session.find(User.class, 1, properties);
//            System.out.println(user.getCompany().getName());
//            System.out.println(user.getUserChats().size());

//        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//             Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            Payment payment = session.find(Payment.class, 1L);
//            payment.setAmount(payment.getAmount() + 10);
//
//            session.getTransaction().commit();
//        }
//
//        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
//
//            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
//                    new Class[]{Session.class},
//                    ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)));
//            session.beginTransaction();
//
////            PaymentRepository paymentRepository = new PaymentRepository(session);
////            paymentRepository.findById(1L).ifPresent(System.out::println);
//
//            session.getTransaction().commit();
//        }
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                    new Class[]{Session.class},
                    ((proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)));
            session.beginTransaction();

            CompanyRepository companyRepository = new CompanyRepository(session);
            CompanyReadMapper companyReadMapper = new CompanyReadMapper();
            UserReadMapper userReadMapper = new UserReadMapper(companyReadMapper);
            UserCreateMapper userCreateMapper = new UserCreateMapper(companyRepository);

            UserRepository userRepository = new UserRepository(session);
            UserService userService = new UserService(userRepository, userReadMapper, userCreateMapper);

            userService.findUserById(1).ifPresent(System.out::println);

            UserCreateDto userCreateDto = new UserCreateDto(
                    PersonalInfo.builder()
                            .firstname("Pavel")
                            .lastname("Durov")
                           // .birthDate(new Birthday(LocalDate.now()))
                            .build(),
                    "pavelDur@gail.co",
                    Role.USER,
                    1
            );
            System.out.println(userService.create(userCreateDto));
            session.getTransaction().commit();
        }


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
//            try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//                Session session = sessionFactory.openSession()) {
//                //User user = User.builder()
////                        .username("bond9191@mail.ru")
////                        .personalInfo(PersonalInfo.builder()
////                                .firstname("Bond")
////                                .lastname("Vlasov")
////                                .birthDate(new Birthday(LocalDate.of(2000,01,01)))
////                                .build())
////                        .role(Role.ADMIN)
////                        .company(session.get(Company.class, 2))
////                        .build();
//                session.beginTransaction();
//                // session.saveOrUpdate(company);
//                //session.saveOrUpdate(user);
//                User user3 = session.get(User.class,1);
//                //session.save(user);
//                session.getTransaction().commit();
//
//            }
            //session.saveOrUpdate(user);
//
        //}
    }
}
