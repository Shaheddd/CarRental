package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.ValidateLicense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationService implements ValidationInterface {

    @Autowired
    ValidationService validationService;

    String line = "";

    @Override
    public List<ValidateLicense> recordLicenseDataToDatabase() {
        try {

            List<ValidateLicense> validateLicenseList = new ArrayList<>();

            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\Shahed's Laptop\\Downloads\\Telegram Desktop\\Car Rental Assignment\\src\\main\\resources\\data.csv"));
            while ((line = bufferedReader.readLine()) != null) {

                String[] data = line.split(",");
                ValidateLicense validateLicense = new ValidateLicense();
                validateLicense.setDriversLicenseName(data[0]);
                validateLicense.setValidateLicenseNumber(data[1]);
                validateLicense.setStatus(data[2]);
                validateLicenseList.add(validateLicense);

            }
            return validateLicenseList;

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;

    }


}
