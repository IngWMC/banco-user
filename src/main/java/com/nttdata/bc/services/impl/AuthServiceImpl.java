package com.nttdata.bc.services.impl;

import org.jboss.logging.Logger;

import com.nttdata.bc.documents.User;
import com.nttdata.bc.models.Auth;
import com.nttdata.bc.models.RedisLogin;
import com.nttdata.bc.repositories.UserRepository;
import com.nttdata.bc.services.IAuthService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthServiceImpl implements IAuthService {
    @Inject
    Logger LOGGER;

    @Inject
    UserRepository repository;

    @Inject
    RedisLoginServiceImpl redisLoginService;

    @Override
    public Uni<User> login(Auth auth) {

        return this.repository.findAll()
                .stream()
                .filter(user -> user.getCardNumber().equals(auth.getCardNumber()) &&
                        user.getPassword().equals(auth.getPassword()))
                .toUni()
                .onItem()
                .ifNotNull()
                .call((user) -> {
                    RedisLogin redisLogin = RedisLogin.builder()
                            .key(auth.getCardNumber())
                            .value(auth.getCardNumber())
                            .build();

                    return this.redisLoginService.insert(redisLogin)
                            .flatMap(a -> {
                                LOGGER.info("redis insert ::: " + a);
                                return Uni.createFrom().item(user);
                            });
                });

    }

}
