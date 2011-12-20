package com.dataart.dao;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public abstract class BaseDao extends JpaDaoSupport{

    @PersistenceUnit
    public void initializeEntityManager(EntityManagerFactory entityManagerFactory) {
        setEntityManagerFactory(entityManagerFactory);
    }
}
