package com.nttdata.bc.services.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import com.nttdata.bc.documents.User;
import com.nttdata.bc.repositories.UserRepository;
import com.nttdata.bc.services.IUserService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserServiceImpl implements IUserService {
    @Inject
    Logger LOGGER;

    @Inject
    UserRepository repository;

    @Override
    public User insert(User obj) {
        this.repository.persist(obj);
        return obj;
    }

    @Override
    public User update(User obj) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> listAll() {
        LOGGER.info("listAll");
        return this.repository.listAll();
    }

    @Override
    public User findById(ObjectId id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(ObjectId id) {
        // TODO Auto-generated method stub

    }

}
