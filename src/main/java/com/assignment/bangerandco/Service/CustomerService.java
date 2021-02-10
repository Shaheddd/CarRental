package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.DataTransferObject.CustomerRegistration;
import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CustomerInterface {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer registerCustomer(CustomerRegistration customerRegistration) {
        Customer customer = new Customer();
        customer.setCustomerFirstName(customerRegistration.getFirstName());
        customer.setCustomerLastName(customerRegistration.getLastName());
        customer.setCustomerAddress(customerRegistration.getAddress());
        customer.setCustomerPhoneNumber(customerRegistration.getPhoneNumber());
        customer.setCustomerAge(customerRegistration.getAge());
        customer.setCustomerEmail(customerRegistration.getEmail());
        return customerRepository.save(customer);
    }

    public Customer getCustomerByID(int customerID) {

        return customerRepository.getById(customerID);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerByUserID(int userID) {
        return customerRepository.getByUserID(userID);
    }

    @Override
    public Customer findCustomers(int customerID) {
        return customerRepository.getOne(customerID);
    }
}
