package com.example.cage_api.service;

import com.example.cage_api.model.Users;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<Object> save(Users user);

    ResponseEntity<Object> findById(long id);
    ResponseEntity<Object> updateUser(long i);

    //List<Users> findByOffline(String st);

    List<Users> fingAllby();

}
