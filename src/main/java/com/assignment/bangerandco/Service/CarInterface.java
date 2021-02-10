package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarInterface {
    void saveCarImage(Car car);

    List<Car> getAllActiveCarImages();

    Optional<Car> getCarImageByID(long id);

    Car getCarByID(long carID);

    List<Car> getAllSmallTownCars();

    List<Car> getAllVehicles();


//    List<Car> findAllCars();
//
//    Car findCarByID(Long carID);
//
//    List<Car> findByCategoryList(String categoryList);
//
//    List<Car> findByTransmissionType(TransmissionType transmissionType);
//
//    List<Car> findByTransmissionTypeAndCategoryType(TransmissionType transmissionType, String categoryList);
//
//    void saveCarToDatabase(MultipartFile multipartFile, String model, String brand, int doors, int seats, boolean airConditioning, TransmissionType transmissionType);
}
