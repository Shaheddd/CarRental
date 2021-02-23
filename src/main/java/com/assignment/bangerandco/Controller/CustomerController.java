package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.DataTransferObject.BookingRegistration;
import com.assignment.bangerandco.Entity.Booking;
import com.assignment.bangerandco.Entity.Car;
import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.User;
import com.assignment.bangerandco.Repository.BookingRepository;
import com.assignment.bangerandco.Service.BookingService;
import com.assignment.bangerandco.Service.CarService;
import com.assignment.bangerandco.Service.CustomerService;
import com.assignment.bangerandco.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CarService carService;

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingRepository bookingRepository;

    @GetMapping("/loadCustomerHomepage")
    public String loadHome(Model model) {
        return "CustomerHomepage";
    }

    @GetMapping("/loadCustomerHomepageBelow25")
    public String loadHomepageBelow25(Model model) {
        return "CustomerHomepageBelow25";
    }

    @GetMapping("/listCars")
    String listCars(Model modelName) {
        List<Car> images = carService.getAllActiveCarImages();
        modelName.addAttribute("carImages", images);
        return "ListVehicles";
    }

    @GetMapping("/getBookingsForCustomer")
    public String getBookingsForCustomer(Model model) {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.getUserByUsername(username);
        Customer customer = customerService.getCustomerByUserID(user.getUserID());

        List<Booking> bookingList = customer.getBookingList();
        model.addAttribute("bookingsForCustomer", bookingList);
        return "GetBookingsForCustomer";
    }

    @GetMapping("/getBookingsForCustomerUnder25")
    public String getBookingsForCustomerUnder25(Model model) {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.getUserByUsername(username);
        Customer customer = customerService.getCustomerByUserID(user.getUserID());

        List<Booking> bookingList = customer.getBookingList();
        model.addAttribute("bookingsForCustomerUnder25", bookingList);
        return "GetBookingsForCustomerUnder25";
    }

    @GetMapping("/extendBooking/{id}")
    public String extendBooking(@PathVariable(value = "id") long bookingID, Model model) {

        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.getUserByUsername(username);
        Customer customer = customerService.getCustomerByUserID(user.getUserID());

        boolean variable = customer.getBookingList().size() > 3;
        if(variable) {
            Booking booking = bookingService.getBookingByID(bookingID);
            booking.setStatus("Extended");
            booking.setEndTime(LocalTime.of(21,00,00,00));
            bookingRepository.save(booking);

        if(booking != null) {
            BookingRegistration bookingRegistration = new BookingRegistration();

            bookingRegistration.setCustomerID(customer.getCustomerID());
            bookingRegistration.setCustomer(customer);

            model.addAttribute("bookingRegistration", bookingRegistration);
            model.addAttribute("booking", booking);
            return "redirect:/customer/getBookingsForCustomer?success";
        }
        }
            return "redirect:/customer/getBookingsForCustomer?failed";

    }


}
