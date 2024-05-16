package com.firsthib.dao;

import com.firsthib.entity.Payment;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class PaymentRepository extends BaseRepository<Long, Payment>{
    public PaymentRepository(EntityManager entityManager) {
        super(Payment.class, entityManager);
    }

    public List<Payment> findAllByReceiverId(Integer receiverId) {
        return getEntityManager().createQuery("select p from Payment p where p.user.id = :receiverId", Payment.class)
                .setParameter("receiverId", receiverId)
                .getResultList();
    }
}
