package com.assignment.bangerandco.DataTransferObject;

import com.assignment.bangerandco.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BookingRegistration {

    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String status;
    private String test;
    private double totalPrice;

    private User user;
    private Car car;
    private Customer customer;
    private Equipment equipment;
    private Booking booking;

    private List<Equipment> equipmentList;
    private int customerID;
    private long carID;
    private long equipmentID;
    private long bookingID;

    private String equipmentName;

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public BookingRegistration(long carID) {
        this.carID = carID;
    }

}
