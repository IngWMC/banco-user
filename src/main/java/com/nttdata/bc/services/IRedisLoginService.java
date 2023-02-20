package com.nttdata.bc.services;

import com.nttdata.bc.models.RedisLogin;

import io.smallrye.mutiny.Uni;

public interface IRedisLoginService {
    Uni<RedisLogin> insert(RedisLogin obj);
}
