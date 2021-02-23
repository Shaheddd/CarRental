package com.assignment.bangerandco.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerID;
    private String customerFirstName;
    private String customerLastName;
    private String customerAddress;
    private String customerPhoneNumber;
    private int customerAge;
    private String customerEmail;
    private String status;
    private String driversLicenseNumber;
    private int userID;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Booking.class)
    private List<Booking> bookingList;

}
