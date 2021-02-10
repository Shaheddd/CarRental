package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.DataTransferObject.AdministratorRegistration;
import com.assignment.bangerandco.DataTransferObject.CustomerRegistration;
import com.assignment.bangerandco.DataTransferObject.EmployeeRegistration;
import com.assignment.bangerandco.Entity.*;
import com.assignment.bangerandco.Service.AdministratorService;
import com.assignment.bangerandco.Service.CustomerService;
import com.assignment.bangerandco.Service.EmployeeService;
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
    EmployeeService employeeService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/loadAdministratorForm")
    public String loadAdministratorForm(Model model) {
        model.addAttribute("administrator", new AdministratorRegistration());
        return "RegisterAdministrator";
    }

    @PostMapping("/registerAdministrator")
    public String registerAdministrator(@ModelAttribute("administrator") AdministratorRegistration administratorRegistration) {

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
    }

    @GetMapping("/loadCustomerForm")
    public String loadCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerRegistration());
        return "RegisterCustomer";
    }

    @PostMapping("/registerCustomer")
    public String registerCustomer(@ModelAttribute("customer") CustomerRegistration customerRegistration) {

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

        return "Login";
    }

    @GetMapping("/loadEmployeeForm")
    public String loadEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeRegistration());
        return "RegisterEmployee";
    }

    @PostMapping("/registerEmployee")
    public String registerEmployee(@ModelAttribute("employee") EmployeeRegistration employeeRegistration) {
        Employee registerEmployee = employeeService.registerEmployee(employeeRegistration);
        User user = new User();
        user.setTableID(registerEmployee.getEmployeeID());
        user.setUsername(employeeRegistration.getUsername());
        user.setPassword(employeeRegistration.getPassword());
        user.setFirstName(employeeRegistration.getFirstName());
        user.setLastName(employeeRegistration.getLastName());
        user.setAddress(employeeRegistration.getAddress());
        user.setPhoneNumber(employeeRegistration.getPhoneNumber());
        user.setRoles(Arrays.asList(new Role("Employee")));
        User registeredUser = userService.save(user);
        registerEmployee.setUserID(registeredUser.getUserID());
        employeeService.save(registerEmployee);

        return "redirect:/employee/loadEmployeeHomepage";
    }

}
