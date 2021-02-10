package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.DataTransferObject.AdministratorRegistration;
import com.assignment.bangerandco.Entity.Administrator;

public interface AdministratorInterface {

    void save(Administrator administrator);

    Administrator registerAdministrator(AdministratorRegistration administratorRegistration);

    void blacklistCustomer(int id);

    void activeCustomer(int id);
}
