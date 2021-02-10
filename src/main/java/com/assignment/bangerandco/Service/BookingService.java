package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Calculations.CalculateBooking;
import com.assignment.bangerandco.DataTransferObject.BookingRegistration;
import com.assignment.bangerandco.Entity.Booking;
import com.assignment.bangerandco.Entity.Car;
import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.Equipment;
import com.assignment.bangerandco.Repository.BookingRepository;
import com.assignment.bangerandco.Repository.CarRepository;
import com.assignment.bangerandco.Repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService implements BookingInterface {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    CarService carService;

    @Autowired
    CustomerService customerService;

    @Autowired
    BookingService bookingService;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    CarRepository carRepository;

    @Override
    public Booking getByID(long id) {
        return bookingRepository.getOne(id);
    }

    @Override
    public Booking getBookingByID(long bookingID) {
        return bookingRepository.getBookingByID(bookingID);
    }


    @Override
    public boolean saveBookingForCustomer(@RequestParam(value = "equipmentID", required = false) List<Long> equipmentList,
                                         BookingRegistration bookingRegistration) {

        try {
            Car car = carService.getCarByID(bookingRegistration.getCarID());
            Customer customer = customerService.getCustomerByID(bookingRegistration.getCustomerID());
            Booking booking = new Booking();
            List<Booking> getBooking = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(bookingRegistration.getStartDate(), formatter);
            LocalDate endDate = LocalDate.parse(bookingRegistration.getEndDate(), formatter);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(bookingRegistration.getStartTime(), timeFormatter);
            LocalTime endTime = LocalTime.parse(bookingRegistration.getEndTime(), timeFormatter);

            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);
            booking.setStatus("Active");

            bookingRegistration.setBooking(booking);

            List<Booking> getAllCarBookings = car.getBookingList();

            if (CalculateBooking.startDateIsBeforeEndDate(booking)) {

                if (!overlapsWithExistingBooking(booking, getAllCarBookings)) {

                    List<Equipment> newEquipmentList = new ArrayList<>();

                    calculateEquipment(equipmentList, newEquipmentList);

                    CalculateBooking.calculateDifferenceInDays(booking);

                    double totalBookingPrice = CalculateBooking.calculateTotalBooking(booking, car, newEquipmentList);
                    booking.setTotalPrice(totalBookingPrice);

                    getBooking = customer.getBookingList();
                    getBooking.add(booking);
                    customer.setBookingList(getBooking);
                    booking.setCar(car);
                    booking.setCustomer(customer);
                    booking.setEquipmentList(newEquipmentList);
                    getBooking = car.getBookingList();
                    getBooking.add(booking);
                    car.setBookingList(getBooking);

                    bookingRepository.save(booking);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public double calculateEquipment(List<Long> equipmentList, List<Equipment> newEquipmentList) {

        if (equipmentList != null) {
            for (Long newEquipment : equipmentList) {
                newEquipmentList.add(equipmentService.findEquipmentByID(newEquipment));
            }

        }
        double calculateEquipments = CalculateBooking.calculateEquipment(newEquipmentList);

        return calculateEquipments;
    }

    public boolean overlapsWithExistingBooking(Booking booking, List<Booking> existingBookings) {
        final LocalDate earlyDate = booking.getStartDate();
        final LocalDate lateDate = booking.getEndDate();
        for (Booking existing : existingBookings) {
            if (!(earlyDate.isAfter(existing.getEndDate())) || lateDate.isBefore(existing.getStartDate())) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }


}

