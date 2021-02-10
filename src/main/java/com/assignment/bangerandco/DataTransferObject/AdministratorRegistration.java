package com.assignment.bangerandco.DataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorRegistration {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
}
