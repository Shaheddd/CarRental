package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.Calculations.CalculateBooking;
import com.assignment.bangerandco.DataTransferObject.BookingRegistration;
import com.assignment.bangerandco.Entity.Car;
import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.Equipment;
import com.assignment.bangerandco.Entity.User;
import com.assignment.bangerandco.Repository.BookingRepository;
import com.assignment.bangerandco.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    CarService carService;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingService bookingService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    EquipmentService equipmentService;

    @GetMapping("/createBooking/{id}")
    public String createBooking(@PathVariable(value = "id") int carID, Model model) {
        try {
            Car car = carService.getCarByID(carID);
            List<Equipment> equipmentList = equipmentService.findAllEquipment();

            String username;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            User user = userService.getUserByUsername(username);
            Customer customer = customerService.getCustomerByUserID(user.getUserID());

            if (car != null) {
                BookingRegistration registration = new BookingRegistration();
                registration.setCarID(car.getId());
                registration.setCar(car);
                registration.setCustomerID(customer.getCustomerID());
                registration.setCustomer(customer);
                model.addAttribute("booking", registration);
                model.addAttribute("equipments", equipmentList);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "AddBooking";
    }

    @PostMapping("/saveBooking")
    public String saveBooking(@ModelAttribute("booking") BookingRegistration bookingRegistration,
                              @RequestParam(value = "equipmentID", required = false) List<Long> equipmentList) {

        boolean save = bookingService.saveBookingForCustomer(equipmentList, bookingRegistration);

        if(save) {
            return "redirect:/customer/loadCustomerHomepage?success";
        } else {
            return "redirect:/customer/loadCustomerHomepage?failed";
        }

//        if (CalculateBooking.startDateIsBeforeEndDate(bookingRegistration.getBooking())) {
//            List<Equipment> newEquipmentList = new ArrayList<>();
//            showEquipment(equipmentList, newEquipmentList);
//            return "redirect:/customer/loadCustomerHomepage?success";
//        } else {
//            return "redirect:/customer/loadCustomerHomepage?failed";
//        }

    }

    @GetMapping("/equipmentCalculation")
    @ResponseBody
    public String showEquipment(@RequestParam List<Long> equipmentList, List<Equipment> newEquipmentList) {

        bookingService.calculateEquipment(equipmentList, newEquipmentList);
        return "ConfirmBooking";

    }

}