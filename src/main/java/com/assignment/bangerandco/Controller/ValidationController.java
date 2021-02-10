package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.Entity.Booking;
import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.User;
import com.assignment.bangerandco.Entity.ValidateLicense;
import com.assignment.bangerandco.Repository.BookingRepository;
import com.assignment.bangerandco.Repository.CarRepository;
import com.assignment.bangerandco.Repository.CustomerRepository;
import com.assignment.bangerandco.Repository.UserRepository;
import com.assignment.bangerandco.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/validation")
public class ValidationController {

    @Autowired
    ValidationService validationService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    BookingService bookingService;

    @Autowired
    CarService carService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @GetMapping("/validateAllDriversLicense/{id}")
    public String validateAllDriversLicense(@PathVariable(value = "id") int customerID) {

        Customer customer = customerService.getCustomerByID(customerID);
        List<ValidateLicense> validateLicenseList = validationService.recordLicenseDataToDatabase();
        List<Booking> bookingList = customer.getBookingList();

        for (ValidateLicense validateLicense : validateLicenseList) {
            if (customer.getDriversLicenseNumber().equals(validateLicense.getValidateLicenseNumber())) {
                customer.setStatus("Blacklist");
                User user = userService.findByTableID(customer.getUserID());
                user.setStatus("Blacklist");
                for (Booking booking:bookingList) {
                    booking.setStatus("Cancelled");
                    bookingRepository.save(booking);
                }
                customerRepository.save(customer);
                userRepository.save(user);

            }
        }

        return "redirect:/administrator/manageCustomers";
    }
}
