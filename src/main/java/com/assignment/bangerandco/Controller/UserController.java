package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.DataTransferObject.AdministratorRegistration;
import com.assignment.bangerandco.DataTransferObject.CustomerRegistration;
import com.assignment.bangerandco.Entity.Administrator;
import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.Role;
import com.assignment.bangerandco.Entity.User;
import com.assignment.bangerandco.Service.AdministratorService;
import com.assignment.bangerandco.Service.CustomerService;
import com.assignment.bangerandco.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AdministratorService administratorService;

    @Autowired
    CustomerService customerService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/loadAdministratorForm")
    public String loadAdministratorForm(Model model) {
        model.addAttribute("administrator", new AdministratorRegistration());
        return "RegisterAdministrator";
    }

    @PostMapping("/registerAdministrator")
    public String registerAdministrator(@ModelAttribute("administrator") AdministratorRegistration administratorRegistration) {

        boolean validateUsername = userService.validateUsername(administratorRegistration.getUsername());

        if(validateUsername) {
            Administrator registerAdministrator = administratorService.registerAdministrator(administratorRegistration);
            User user = new User();
            user.setTableID(registerAdministrator.getAdministratorID());
            user.setUsername(administratorRegistration.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(administratorRegistration.getPassword()));
            user.setFirstName(administratorRegistration.getFirstName());
            user.setLastName(administratorRegistration.getLastName());
            user.setPhoneNumber(administratorRegistration.getPhoneNumber());
            user.setAddress(administratorRegistration.getAddress());
            user.setStatus("Active");
            user.setRoles(Arrays.asList(new Role("Administrator")));
            User registeredUser = userService.save(user);
            registerAdministrator.setUserID(registeredUser.getUserID());
            administratorService.save(registerAdministrator);

            return "redirect:/administrator/loadAdministratorHomepage";
        } else {
            return "redirect:/user/loadAdministratorForm?failed";
        }

    }

    @GetMapping("/loadCustomerForm")
    public String loadCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerRegistration());
        return "RegisterCustomer";
    }

    @PostMapping("/registerCustomer")
    public String registerCustomer(@ModelAttribute("customer") CustomerRegistration customerRegistration) {

        boolean validateUsername = userService.validateUsername(customerRegistration.getUsername());

        if(validateUsername) {
            Customer registerCustomer = customerService.registerCustomer(customerRegistration);
            User user = new User();
            user.setTableID(registerCustomer.getCustomerID());
            user.setUsername(customerRegistration.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(customerRegistration.getPassword()));
            user.setFirstName(customerRegistration.getFirstName());
            user.setLastName(customerRegistration.getLastName());
            user.setPhoneNumber(customerRegistration.getPhoneNumber());
            user.setAddress(customerRegistration.getAddress());
            user.setAge(customerRegistration.getAge());
            user.setStatus("Active");
            registerCustomer.setStatus("Active");
            registerCustomer.setDriversLicenseNumber(customerRegistration.getDriversLicenseNumber());
            user.setRoles(Arrays.asList(new Role("Customer")));
            User registeredUser = userService.save(user);
            registerCustomer.setUserID(registeredUser.getUserID());
            customerService.save(registerCustomer);
        } else {
            return "redirect:/user/loadCustomerForm?failed";
        }

        return "Login";
    }

}
