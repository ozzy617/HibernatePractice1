package com.firsthib.dao;

import com.firsthib.entity.User;

import javax.persistence.EntityManager;

public class UserRepository extends BaseRepository<Integer, User>{
    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
