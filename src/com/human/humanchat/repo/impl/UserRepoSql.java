package com.human.humanchat.repo.impl;


import com.human.humanchat.model.User;
import com.human.humanchat.util.DatabaseConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepoSql implements UserRepo {
    @Override
    public boolean exits(String email) throws SQLException {
        String query = "select count(*) from users_chat where email = ?";
        try (Connection connection = DatabaseConnectionFactory.getInstance().openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) == 1;
            }
        }
    }


    private static User toUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    @Override
    public void insert(User user) throws SQLException {
        String query = "insert into users_chat(name,surname,email,password) values (?,?,?,?);";
        try (Connection connection = DatabaseConnectionFactory.getInstance().openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.execute();
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws SQLException {
        String query = "select * from users_chat where email = ? and password = ?";
        try (Connection connection = DatabaseConnectionFactory.getInstance().openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
               if(resultSet.next()){
                   return Optional.of(UserRepoSql.toUser(resultSet));
               }
            }
        }
        return  Optional.empty();
    }
}