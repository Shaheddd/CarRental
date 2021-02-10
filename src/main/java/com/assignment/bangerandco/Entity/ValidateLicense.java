package com.assignment.bangerandco.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateLicense {

    private int driversLicenseID;
    private String driversLicenseName;
    private String validateLicenseNumber;
    private String status;

}
