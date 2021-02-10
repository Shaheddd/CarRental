package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.DataTransferObject.BookingRegistration;
import com.assignment.bangerandco.Entity.Booking;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookingInterface {

    Booking getByID(long id);

    Booking getBookingByID(long bookingID);


    boolean saveBookingForCustomer(@RequestParam(value = "equipmentID", required = false) List<Long> equipmentList,
                                  BookingRegistration bookingRegistration);

}
