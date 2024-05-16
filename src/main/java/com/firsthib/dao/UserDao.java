package com.firsthib.dao;


import com.firsthib.dto.PaymentFilter;
import com.firsthib.entity.*;
import com.firsthib.util.HibernateUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.sql.JPASQLQuery;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;

public class UserDao {
    private static final UserDao INSTANCE = new UserDao();

    public List<User> findAll(Session session) {
////        CriteriaBuilder cb = session.getCriteriaBuilder();
////        var criteria = cb.createQuery(User.class);
////        var user = criteria.from(User.class);
////        criteria.select(user);
//        //return session.createQuery(criteria).list();
       /// QUser user = QUser.user;
        //HibernateQuery<User> query= new HibernateQuery<>(session);
        //HibernateQuery<User>0 query = new HibernateQuery<>(session);
        return new JPAQuery<User>(session).select(QUser.user).from(QUser.user).fetch();
    }
//
    public List<User> findAllByFirstName(Session session, String firstname) {
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        var criteria = cb.createQuery(User.class);
//        var user = criteria.from(User.class);
        //criteria.select(user).where(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstname), firstname));
        //return session.createQuery(criteria).list();

        return new JPAQuery<User>(session).select(QUser.user).from(QUser.user)
                .where(QUser.user.personalInfo.firstname.eq(firstname)).fetch();
    }

    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return new JPAQuery<User>(session).select(QUser.user).from(QUser.user)
                .orderBy(new OrderSpecifier(Order.ASC, QUser.user.personalInfo.birthDate))
                .limit(limit).fetch();
    }

    public List<User> findAllByCompanyName(Session session, String companyName) {
            return new JPAQuery<User>(session).select(QUser.user).from(QCompany.company)
                    .join(QCompany.company.users, QUser.user)
                    .where(QCompany.company.name.eq(companyName))
                    .fetch();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return new JPAQuery<Payment>(session)
                .select(QPayment.payment)
                .from(QCompany.company)
                .join(QCompany.company.users, QUser.user)
                .join(QUser.user.payment, QPayment.payment)
                .where(QCompany.company.name.eq(companyName))
                .orderBy(QUser.user.personalInfo.firstname.asc(), QPayment.payment.amount.asc())
                .fetch();
    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, PaymentFilter filter) {
//        List<Predicate> predicates = new ArrayList<>();
//        if (filter.getFirstname() != null) {
//            predicates.add(QUser.user.personalInfo.firstname.eq(filter.getFirstname()));
//        }
//        if (filter.getLastname() != null) {
//            predicates.add(QUser.user.personalInfo.lastname.eq(filter.getLastname()));
//        }
        var predicate = QPredicate.builder()
                .add(filter.getFirstname(), QUser.user.personalInfo.firstname::eq)
                .add(filter.getLastname(), QUser.user.personalInfo.lastname::eq)
                .buildAnd();

        return new JPAQuery<Double>(session)
                .select(QPayment.payment.amount.avg())
                .from(QPayment.payment)
                .join(QPayment.payment.user, QUser.user)
                .where(predicate)
                .fetchOne();
    }

    public List<Tuple> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return new JPAQuery<com.querydsl.core.Tuple>(session)
                .select(QCompany.company.name, QPayment.payment.amount.avg())
                .from(QCompany.company)
                .join(QCompany.company.users, QUser.user)
                .join(QUser.user.payment, QPayment.payment)
                .groupBy(QCompany.company.name)
                .orderBy(QCompany.company.name.asc())
                .fetch();
    }

    public List<Tuple> isItPossible(Session session) {
        return new JPAQuery<Tuple>(session)
                .select(QUser.user, QPayment.payment.amount.avg())
                .from(QUser.user)
                .join(QUser.user.payment, QPayment.payment)
                .groupBy(QUser.user.id)
                .having(QPayment.payment.amount.avg().gt(
                        new JPAQuery<Double>(session)
                                .select(QPayment.payment.amount.avg())
                                .from(QPayment.payment)
                ))
                .orderBy(QUser.user.personalInfo.firstname.asc())
                .fetch();
    }
    public static UserDao getInstance() {
        return INSTANCE;
    }
}
