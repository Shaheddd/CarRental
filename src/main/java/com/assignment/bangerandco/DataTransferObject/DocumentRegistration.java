package com.assignment.bangerandco.DataTransferObject;

import com.assignment.bangerandco.Entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRegistration {

    private String documentName;
    private String documentType;

    private Customer customer;
}
