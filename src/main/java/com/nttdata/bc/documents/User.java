package com.nttdata.bc.documents;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "users")
public class User {
    private ObjectId id;

    @NotEmpty(message = "El campo documentIdentityType es requerido.")
    @Pattern(regexp = "^DNI$|^CEX$|^RUC$", message = "El campo documentIdentityType debe tener uno de estos valores: [DNI, CEX, RUC].")
    private String documentIdentityType;

    @NotEmpty(message = "El campo documentIdentity es requerido.")
    private String documentIdentity;

    @NotEmpty(message = "El campo cardNumber es requerido.")
    @BsonProperty("cardNumber")
    private String cardNumber; // número de la tarjeta

    @NotEmpty(message = "El campo expirationDate es requerido.")
    @BsonProperty("expirationDate")
    private String expirationDate; // fecha de vencimiento

    @NotEmpty(message = "El campo cardValidationCode es requerido.")
    @BsonProperty("cardValidationCode")
    private String cardValidationCode; // código de validación de la tarjeta

    @NotEmpty(message = "El campo password es requerido.")
    private String password;

    @BsonProperty("createdAt")
    private LocalDate createdAt;

    @BsonProperty("updateddAt")
    private LocalDate updateddAt;
}
