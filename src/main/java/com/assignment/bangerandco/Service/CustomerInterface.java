package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.DataTransferObject.CustomerRegistration;
import com.assignment.bangerandco.Entity.Customer;

import java.util.List;

public interface CustomerInterface {
    void save(Customer customer);

    Customer registerCustomer(CustomerRegistration customerRegistration);

    List<Customer> getAllCustomers();

    Customer getCustomerByUserID(int userID);

    Customer findCustomers(int customerID);
}
