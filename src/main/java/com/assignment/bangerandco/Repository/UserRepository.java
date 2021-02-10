package com.assignment.bangerandco.Repository;

import com.assignment.bangerandco.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT user from User user where user.username = ?1")
    User searchUsername(String username);

    @Query("SELECT user from User user where user.tableID = ?1")
    User getByTableID(int id);
}
