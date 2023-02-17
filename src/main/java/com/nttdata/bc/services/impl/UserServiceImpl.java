package com.nttdata.bc.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.nttdata.bc.clients.IAccountRestClient;
import com.nttdata.bc.clients.IClientRestClient;
import com.nttdata.bc.documents.User;
import com.nttdata.bc.exceptions.BadRequestException;
import com.nttdata.bc.exceptions.NotFoundException;
import com.nttdata.bc.models.Client;
import com.nttdata.bc.models.DebitCard;
import com.nttdata.bc.repositories.UserRepository;
import com.nttdata.bc.services.IUserService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserServiceImpl implements IUserService {
    @Inject
    Logger LOGGER;

    @Inject
    UserRepository repository;

    @Inject
    @RestClient
    IClientRestClient clientRestClient;

    @Inject
    @RestClient
    IAccountRestClient accountRestClient;

    @Override
    public Uni<User> insert(User obj) {
        return this.accountRestClient.findByCardNumber(obj.getCardNumber())
                .onItem().ifNull()
                .failWith(() -> new NotFoundException("La tarjeta con número: " + obj.getCardNumber() + ", no existe."))
                .flatMap(account -> {
                    DebitCard debitCard = account.getDebitCard();
                    return this.validateDebitCard(debitCard, obj)
                            .flatMap((dc) -> {
                                return this.clientRestClient.fintById(account.getClientId())
                                        .onItem().ifNull()
                                        .failWith(() -> new NotFoundException("El cliente no existe."))
                                        .flatMap(client -> {
                                            return this.validateClient(client, obj)
                                                    .flatMap(c -> {
                                                        if (obj.getPassword().length() != 6)
                                                            return Uni.createFrom()
                                                                    .failure(new BadRequestException(
                                                                            "El campo password debe contener 6 caracteres."));

                                                        obj.setIsActive(true);
                                                        obj.setCreatedAt(LocalDate.now());
                                                        return this.repository.persist(obj);
                                                    });
                                        });
                            });
                });
    }

    @Override
    public Uni<User> update(User obj) {
        if (obj.getId() == null)
            return Uni.createFrom().failure(new BadRequestException("El campo id es requerido."));

        return this.repository.findById(obj.getId())
                .onItem().ifNull()
                .failWith(() -> new NotFoundException("El usuario con id: " + obj.getId() + ", no existe."))
                .flatMap(user -> {
                    return this.accountRestClient.findByCardNumber(obj.getCardNumber())
                            .onItem().ifNull()
                            .failWith(() -> new NotFoundException(
                                    "La tarjeta con número: " + obj.getCardNumber() + ", no existe."))
                            .flatMap(account -> {
                                DebitCard debitCard = account.getDebitCard();
                                return this.validateDebitCard(debitCard, obj)
                                        .flatMap((dc) -> {
                                            return this.clientRestClient.fintById(account.getClientId())
                                                    .onItem().ifNull()
                                                    .failWith(() -> new NotFoundException("El cliente no existe."))
                                                    .flatMap(client -> {
                                                        return this.validateClient(client, obj)
                                                                .flatMap(c -> {
                                                                    if (obj.getPassword().length() != 6)
                                                                        return Uni.createFrom()
                                                                                .failure(new BadRequestException(
                                                                                        "El campo password debe contener 6 caracteres."));

                                                                    user.setPassword(obj.getPassword());
                                                                    user.setUpdateddAt(LocalDate.now());
                                                                    return this.repository.update(user);
                                                                });
                                                    });
                                        });
                            });
                });
    }

    @Override
    public Uni<List<User>> listAll() {
        return this.repository.listAll();
    }

    @Override
    public Uni<User> findById(ObjectId id) {
        return this.repository.findById(id)
                .onItem().ifNull()
                .failWith(() -> new NotFoundException("El usuario con id: " + id + ", no existe"));
    }

    @Override
    public Uni<Void> delete(ObjectId id) {
        return this.repository.findById(id)
                .onItem().ifNull()
                .failWith(() -> new NotFoundException("El usuario con id: " + id + ", no existe"))
                .flatMap(user -> {
                    user.setIsActive(false);
                    return this.repository.update(user);
                })
                .replaceWithVoid();
    }

    private Uni<Void> validateDebitCard(DebitCard debitCard, User user) {

        if (!debitCard.getExpirationDate().equals(user.getExpirationDate()))
            return Uni.createFrom()
                    .failure(new BadRequestException("El campo expirationDate es incorrecto."));
        else if (!debitCard.getCardValidationCode().equals(user.getCardValidationCode()))
            return Uni.createFrom()
                    .failure(new BadRequestException("El campo cardValidationCode es incorrecto."));
        else if (!debitCard.getPin().equals(user.getPin()))
            return Uni.createFrom()
                    .failure(new BadRequestException("El campo pin es incorrecto."));
        else if (debitCard.getIsActive() == false)
            return Uni.createFrom()
                    .failure(new BadRequestException(
                            "La tarjeta está bloqueado."));

        return Uni.createFrom().item(debitCard)
                .replaceWithVoid();
    }

    private Uni<Void> validateClient(Client client, User user) {

        if (!client.getDocumentIdentityType().equals(user.getDocumentIdentityType()))
            return Uni.createFrom()
                    .failure(new BadRequestException(
                            "El campo documentIdentityType es incorrecto."));
        else if (!client.getDocumentIdentity().equals(user.getDocumentIdentity()))
            return Uni.createFrom()
                    .failure(new BadRequestException(
                            "El campo documentIdentity es incorrecto."));

        return Uni.createFrom().item(client)
                .replaceWithVoid();
    }
}
