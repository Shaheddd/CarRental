package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserInterface extends UserDetailsService {

    User save(User user);

    User searchUserByUsername(String username);

    User findByTableID(int userID);

    User deleteByTableID(int id);

    List<User> getAllUsers();

    User getUserByUsername(String username);

    boolean validateUsername(String userName);
}
