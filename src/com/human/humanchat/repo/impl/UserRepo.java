package com.human.humanchat.repo.impl;



import com.human.humanchat.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepo {
    boolean  exits(String email) throws SQLException;
    void insert(User user) throws SQLException;
    Optional<User> findByEmailAndPassword(String email,String password) throws  SQLException;

}
