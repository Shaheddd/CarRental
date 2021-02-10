package com.assignment.bangerandco.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cars")
public class Car {

    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String model;
    private String brand;
    private String categoryType;
    private double categoryPrice;
    private String fuelType;
//    private String transmissionType;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    private String airConditioning;
    private int seats;
    private int doors;

    @Lob
    private byte[] image;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Booking.class)
    private List<Booking> bookingList;

    @Override
    public String toString() {
        return "Car [carID = " + id + "model = " + model + "brand = " + brand + "transmissionType = " + transmissionType + "airConditioning = "
                + airConditioning + "seats = " + seats + "doors = " + doors + "image =  + Arrays.toString(image)" + "]";
    }


}
