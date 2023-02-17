package com.nttdata.bc.documents;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@MongoEntity(collection = "users")
public class User {
    private ObjectId id;

    @NotEmpty(message = "El campo documentIdentityType es requerido.")
    @Pattern(regexp = "^DNI$|^CEX$|^RUC$", message = "El campo documentIdentityType debe tener uno de estos valores: [DNI, CEX, RUC].")
    @BsonIgnore
    private String documentIdentityType;

    @NotEmpty(message = "El campo documentIdentity es requerido.")
    @BsonIgnore
    private String documentIdentity;

    @NotEmpty(message = "El campo cardNumber es requerido.")
    @BsonProperty("cardNumber")
    private String cardNumber; // número de la tarjeta

    @NotEmpty(message = "El campo expirationDate es requerido.")
    @BsonIgnore
    private String expirationDate; // fecha de vencimiento

    @NotEmpty(message = "El campo cardValidationCode es requerido.")
    @BsonIgnore
    private String cardValidationCode; // código de validación de la tarjeta

    @NotEmpty(message = "El campo pin es requerido.")
    @BsonIgnore
    private String pin;

    @NotEmpty(message = "El campo password es requerido.")
    private String password;

    @BsonProperty("isActive")
    private Boolean isActive;

    @BsonProperty("createdAt")
    private LocalDate createdAt;

    @BsonProperty("updateddAt")
    private LocalDate updateddAt;
}
