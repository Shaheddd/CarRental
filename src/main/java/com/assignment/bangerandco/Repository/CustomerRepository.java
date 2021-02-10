package com.assignment.bangerandco.Repository;

import com.assignment.bangerandco.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT customer from Customer customer where customer.customerID = ?1")
    Customer getById(int id);

    @Query("SELECT customer from Customer customer where customer.userID = ?1")
    Customer getByUserID(int userID);

}
