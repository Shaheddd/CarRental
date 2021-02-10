package com.assignment.bangerandco.Repository;

import com.assignment.bangerandco.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT booking from Booking booking where booking.bookingID = ?1")
    Booking getBookingByID(long bookingID);

    @Query("SELECT booking from Booking booking where booking.customer.customerID = ?1")
    List<Booking> getAllCustomerBookings(int customerID);

    @Query("SELECT car from Booking car where car.car.id = ?1")
    List<Booking> getAllCars(long carID);


}
