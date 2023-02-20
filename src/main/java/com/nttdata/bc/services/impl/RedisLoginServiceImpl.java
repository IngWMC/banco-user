package com.nttdata.bc.services.impl;

import org.jboss.logging.Logger;

import com.nttdata.bc.models.RedisLogin;
import com.nttdata.bc.services.IRedisLoginService;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RedisLoginServiceImpl implements IRedisLoginService {
    @Inject
    Logger LOGGER;

    @Inject
    ReactiveRedisDataSource reactiveRedisDataSource;

    @Override
    public Uni<RedisLogin> insert(RedisLogin obj) {
        return this.reactiveRedisDataSource.value(String.class).set(obj.getKey(), obj.getValue())
                .flatMap((v) -> Uni.createFrom().item(obj));
    }

    public Uni<String> findByKey(String key) {
        LOGGER.info("entro");
        return this.reactiveRedisDataSource.key().keys(key)
                .flatMap(val -> {
                    LOGGER.info("entro val " + val.toString());
                    return Uni.createFrom().item(val);
                })
                .onItem()
                .transform(v -> {
                    LOGGER.info("entro v " + v.get(0).length());
                    return v.get(0);
                });
    }

}
