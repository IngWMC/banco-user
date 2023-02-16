package com.nttdata.bc.services;

import org.bson.types.ObjectId;

import com.nttdata.bc.documents.User;

public interface IUserService extends ICRUD<User, ObjectId> {

}
