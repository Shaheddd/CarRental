package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.Role;
import com.assignment.bangerandco.Entity.User;
import com.assignment.bangerandco.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User searchUserByUsername(String username) {
        return userRepository.searchUsername(username);
    }

    @Override
    public User findByTableID(int userID) {
        return userRepository.getOne(userID);
    }

    @Override
    public User deleteByTableID(int id) {
        return userRepository.getByTableID(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.searchUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.searchUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username Not Found!");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

    }

    @Override
    public boolean validateUsername(String userName){
        try {
            User user = userRepository.searchUsername(userName);

            if(userName == null) {
                return true;
            }
    } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
