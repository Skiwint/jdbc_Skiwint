package com.example.cage_api.service;

import com.example.cage_api.exception.UserExistingEmailException;
import com.example.cage_api.exception.UserNotFoundException;
import com.example.cage_api.model.Users;
import com.example.cage_api.repo.UserRepo;
import com.example.cage_api.response.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ConnectionClass implements UserService{

    JdbcTemplate jdbcTemplate;
    JdbcOperations jdbcOperations;
    UserRepo userRepo;


    @Override
    public ResponseEntity<Object> save(Users user) {
        if(user.getEmail().equals(userRepo.findByEmail(user.getEmail()).get().getEmail())) {
            throw new UserExistingEmailException("there is user with the same email");
        }
        long id = userRepo.save(user).getId();
        return ResponseHandler.responseBuilder("user was saved", HttpStatus.OK, id);
    }

    @Override
    public ResponseEntity<Object> findById(long id) {
       if(!userRepo.findById(id).isPresent())
           throw new UserNotFoundException("User not exist");

        return ResponseHandler.responseBuilder("searched user", HttpStatus.OK,userRepo.findById(id).get());
    }




    @Override
    public ResponseEntity<Object> updateUser(long id) {
        if(!userRepo.findById(id).isPresent())
            throw new UserNotFoundException("User not exist");

        Users users = userRepo.findById(id).get();
        Map<String, String> updateResponse = new HashMap<>();
        updateResponse.put("id", ""+users.getId());
        updateResponse.put("previous status", users.getStatus());
        if(users.getStatus().equalsIgnoreCase("offline")){
            users.setStatus("online");
            updateResponse.put("current status", users.getStatus());
        } else{
            users.setStatus("offline");
            updateResponse.put("current status", users.getStatus());
        }

        userRepo.save(users);
        return ResponseHandler.responseBuilder("user was updated", HttpStatus.OK, updateResponse);
    }


//    @Override
//    public List<Users> findByOffline(String st) {
//        String sql = "SELECT * FROM users WHERE status = ?";
//        List<Users> users = this.jdbcTemplate.query("SELECT * FROM users WHERE status = ?", new Object[]{st}, new RowMapper<Users>() {
//            @Override
//            public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Users users1 = new Users();
//                users1.setName(rs.getString("name"));
//                users1.setSerName(rs.getString("serName"));
//                users1.setEmail(rs.getString("email"));
//                users1.setImageUrl(rs.getString("imageUrl"));
//                users1.setStatus(rs.getString("setStatus"));
//                return users1;
//            }
//        });
//        return users;
//    }

    @Override
    public List<Users> fingAllby() {
        //jdbcOperations.query("SELECT id, name, ser_name, email, image_url, status FROM users", this::mapRow);
        //jdbcTemplate.query("SELECT id, name, ser_name, email, image_url, status FROM users", this::mapRow);
        return jdbcOperations.query("SELECT id, name, ser_name, email, image_url, status FROM users", this::mapRow);
    }

    @Override
    public List<Users> findByStatus(String status) {
        String query = "SELECT * FROM users WHERE status = ?";
        List<Users> users = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, status);

            ResultSet resultSet = ps.executeQuery();


            while (resultSet.next()){
                Users user = new Users();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSerName(resultSet.getString("ser_name"));
                user.setEmail(resultSet.getString("email"));
                user.setImageUrl(resultSet.getString("image_url"));
                user.setStatus(resultSet.getString("status"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private Users mapRow(ResultSet row, int rowNum)
            throws SQLException {
        return new Users(
                row.getLong("id"),
                row.getString("name"),
                row.getString("ser_name"),
                row.getString("email"),
                row.getString("image_url"),
                row.getString("status"));
    };
}
