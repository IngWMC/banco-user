package com.nttdata.bc.services;

import com.nttdata.bc.documents.User;
import com.nttdata.bc.models.Auth;

import io.smallrye.mutiny.Uni;

public interface IAuthService {
    Uni<User> login(Auth auth);
}
