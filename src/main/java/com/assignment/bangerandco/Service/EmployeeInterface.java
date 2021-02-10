package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.DataTransferObject.EmployeeRegistration;
import com.assignment.bangerandco.Entity.Employee;

public interface EmployeeInterface {
    void save(Employee employee);

    Employee registerEmployee(EmployeeRegistration employeeRegistration);
}
