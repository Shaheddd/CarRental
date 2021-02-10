package com.assignment.bangerandco.Calculations;

import com.assignment.bangerandco.Entity.Booking;
import com.assignment.bangerandco.Entity.Car;
import com.assignment.bangerandco.Entity.Equipment;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalculateBooking {

    public static long calculateDifferenceInDays(Booking booking) {
        LocalDate startLocalDate = booking.getStartDate();
        LocalDate endLocalDate = booking.getEndDate();

        long differenceOfDays = DAYS.between(startLocalDate, endLocalDate);

        return differenceOfDays;

    }

    public static double calculateEquipment(List<Equipment> equipmentList) {
        double totalEquipmentPrice = 0.0;

        for (Equipment equipment : equipmentList) {

            totalEquipmentPrice += equipment.getPrice();
        }

        return totalEquipmentPrice;
    }

    public static double calculateCarPrice(Booking booking, Car car) {
        double totalCarPrice = car.getCategoryPrice();
        long amountOfDays = calculateDifferenceInDays(booking);

        return (totalCarPrice * amountOfDays);
    }

    public static double calculateTotalBooking(Booking booking, Car car, List<Equipment> equipmentList) {

        return calculateEquipment(equipmentList) + calculateCarPrice(booking, car);
    }

    public static boolean startDateIsBeforeEndDate(Booking booking) {
        if (booking.getStartDate().compareTo(booking.getEndDate()) > 0) {
            return false;
        } else {
            return true;
        }
    }

}
