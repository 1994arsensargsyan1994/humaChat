package com.human.humanchat.service;



import com.human.humanchat.exception.DatabaseException;
import com.human.humanchat.model.User;

import java.util.Optional;

public interface UserService {
    boolean userExist(String email) throws DatabaseException;
    void save(User user) throws DatabaseException;
    Optional<User> getUser(String emil,String password) throws  DatabaseException;
}
