package com.nttdata.bc.resources;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse.StatusCode;

import com.nttdata.bc.documents.User;
import com.nttdata.bc.models.Auth;
import com.nttdata.bc.services.IAuthService;
import com.nttdata.bc.services.impl.RedisLoginServiceImpl;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
public class AuthResource {
    @Inject
    Logger LOGGER;

    @Inject
    IAuthService service;

    @Inject
    RedisLoginServiceImpl redisLoginService;

    @POST
    @Path("/login")
    @ResponseStatus(StatusCode.OK)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<User> login(@Valid Auth obj) {
        return this.service.login(obj);
    }

    @GET
    @Path("/{key}")
    @ResponseStatus(StatusCode.OK)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> findByKey(@PathParam("key") String key) {
        LOGGER.info("entro");
        return this.redisLoginService.findByKey(key);
    }

}
