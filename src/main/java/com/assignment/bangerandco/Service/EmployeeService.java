package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.DataTransferObject.EmployeeRegistration;
import com.assignment.bangerandco.Entity.Employee;
import com.assignment.bangerandco.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements EmployeeInterface {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee registerEmployee(EmployeeRegistration employeeRegistration) {
        Employee employee = new Employee();
        employee.setEmployeeFirstName(employeeRegistration.getFirstName());
        employee.setEmployeeLastName(employeeRegistration.getLastName());
        employee.setEmployeeAddress(employeeRegistration.getAddress());
        employee.setEmployeePhoneNumber(employeeRegistration.getPhoneNumber());
        return employeeRepository.save(employee);
    }


}
