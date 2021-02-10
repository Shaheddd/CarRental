package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.Entity.User;
import com.assignment.bangerandco.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loadLogin() {
        return "Login";
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        String username;

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (object instanceof UserDetails) {
            username = ((UserDetails) object).getUsername();
        } else {
            username = object.toString();
        }

        User user = userService.searchUserByUsername(username);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Administrator"))) {
            model.addAttribute("listUsers", userService.getAllUsers());
            return "redirect:/administrator/loadAdministratorHomepage";
        }

        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Customer"))) {

            if (user.getAge() > 25 && !user.getStatus().equals("Blacklist")) {
                return "redirect:/customer/loadCustomerHomepage";
            }
            if (user.getAge() < 25 && !user.getStatus().equals("Blacklist")) {
                return "redirect:/customer/loadCustomerHomepageBelow25";
            } else {
                return "BlacklistPage";
            }
        }

        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("Employee"))) {

            return "redirect:/employee/loadEmployeeHomepage";
        }

        return "Login";
    }
}
