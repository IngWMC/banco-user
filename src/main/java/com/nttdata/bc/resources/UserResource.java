package com.nttdata.bc.resources;

import java.util.List;

import org.bson.types.ObjectId;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse.StatusCode;

import com.nttdata.bc.documents.User;
import com.nttdata.bc.services.IUserService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class UserResource {
    @Inject
    Logger LOGGER;

    @Inject
    IUserService service;

    @POST
    @Path("/register")
    @ResponseStatus(StatusCode.CREATED)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Uni<User> insert(@Valid User obj) {
        return this.service.insert(obj);
    }

    @PUT
    @Path("/{id}")
    @ResponseStatus(StatusCode.CREATED)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Uni<User> update(@PathParam("id") ObjectId id, @Valid User obj) {
        obj.setId(id);
        return this.service.update(obj);
    }

    @GET
    @ResponseStatus(StatusCode.OK)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<User>> listAll() {
        return this.service.listAll();
    }

    @GET
    @Path("/{id}")
    @ResponseStatus(StatusCode.OK)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<User> findById(@PathParam("id") ObjectId id) {
        return this.service.findById(id);
    }

    @DELETE
    @Path("/{id}")
    @ResponseStatus(StatusCode.NO_CONTENT)
    public Uni<Void> delete(@PathParam("id") ObjectId id) {
        return this.service.delete(id);
    }
}
