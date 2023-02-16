package com.nttdata.bc.resources;

import java.util.List;

import org.jboss.logging.Logger;

import com.nttdata.bc.documents.User;
import com.nttdata.bc.services.IUserService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/users")
public class UserResource {
    @Inject
    Logger LOGGER;

    @Inject
    IUserService service;

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response insert(@Valid User obj) {
        User user = this.service.insert(obj);
        return Response.status(Status.CREATED).entity(user).build();
    }

    // @PUT
    // @Path("/{id}")
    // @Produces(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Transactional
    // public Response update(@PathParam("id") Integer id, @Valid Account obj) {
    // logger.info("Inicio ::: update ::: " + obj);
    // obj.setAccountId(id);
    // Account account = this.service.update(obj);
    // return Response.status(Status.CREATED).entity(account).build();
    // }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        LOGGER.info("listAll");
        List<User> users = this.service.listAll();
        return Response.ok(users).build();
    }

    // @GET
    // @Path("/{id}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response findById(@PathParam("id") Integer id) {
    // Account account = this.service.findById(id);
    // return Response.ok(account).build();
    // }

    // @PUT
    // @Path("/{id}")
    // public Response delete(@PathParam("id") Integer id) {
    // this.service.delete(id);
    // return Response.noContent().build();
    // }
}
