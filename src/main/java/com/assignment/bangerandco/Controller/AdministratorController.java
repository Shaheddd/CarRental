package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.Service.AdministratorService;
import com.assignment.bangerandco.Service.CustomerService;
import com.assignment.bangerandco.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    @Autowired
    AdministratorService administratorService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @GetMapping("/loadAdministratorHomepage")
    public String loadHome(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "AdministratorHomepage";
    }

    @GetMapping("/blacklistCustomer/{id}")
    public String blacklistCustomer(@PathVariable(value = "id") int id) {
        administratorService.blacklistCustomer(id);
        return "redirect:/administrator/manageCustomers";
    }

    @GetMapping("/activeCustomer/{id}")
    public String activeCustomer(@PathVariable(value = "id") int id) {
        administratorService.activeCustomer(id);
        return "redirect:/administrator/manageCustomers";
    }

    @GetMapping("/manageCustomers")
    public String listAllCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "ManageCustomers";
    }

}
