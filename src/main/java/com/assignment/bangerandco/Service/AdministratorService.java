package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.DataTransferObject.AdministratorRegistration;
import com.assignment.bangerandco.Entity.Administrator;
import com.assignment.bangerandco.Entity.Booking;
import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.User;
import com.assignment.bangerandco.Repository.AdministratorRepository;
import com.assignment.bangerandco.Repository.BookingRepository;
import com.assignment.bangerandco.Repository.CustomerRepository;
import com.assignment.bangerandco.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService implements AdministratorInterface {

    @Autowired
    AdministratorRepository administratorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public void save(Administrator administrator) {
        administratorRepository.save(administrator);
    }

    @Override
    public Administrator registerAdministrator(AdministratorRegistration administratorRegistration) {
        Administrator administrator = new Administrator();
        administrator.setAdministratorFirstName(administratorRegistration.getFirstName());
        administrator.setAdministratorLastName(administratorRegistration.getLastName());
        administrator.setAdministratorAddress(administratorRegistration.getAddress());
        administrator.setAdministratorPhoneNumber(administratorRegistration.getPhoneNumber());
        return administratorRepository.save(administrator);
    }

    @Override
    public void blacklistCustomer(int id) {
        User user = userRepository.getOne(id);
        Customer customer = customerRepository.getByUserID(id);
        user.setStatus("Blacklist");
        customer.setStatus("Blacklist");
        List<Booking> bookingList = customer.getBookingList();
        for(Booking booking : bookingList) {
            booking.setStatus("Cancelled");
        }
        userRepository.save(user);
        customerRepository.save(customer);
    }

    @Override
    public void activeCustomer(int id) {
        User user = userRepository.getOne(id);
        Customer customer = customerRepository.getByUserID(id);
        List<Booking> bookingList = customer.getBookingList();
        for(Booking booking : bookingList) {
            booking.setStatus("Active");
        }
        user.setStatus("Active");
        customer.setStatus("Active");

        userRepository.save(user);
        customerRepository.save(customer);
    }
}
