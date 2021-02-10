package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.Car;
import com.assignment.bangerandco.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService implements CarInterface {

    @Autowired
    CarRepository carRepository;

    @Override
    public void saveCarImage(Car car) {
        carRepository.save(car);
    }

    @Override
    public List<Car> getAllActiveCarImages() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarImageByID(long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car getCarByID(long carID) {

        return carRepository.getById(carID);
    }

    @Override
    public List<Car> getAllSmallTownCars() {
        return carRepository.getSmallTownCars();
    }

    @Override
    public List<Car> getAllVehicles() {
        return carRepository.findAll();
    }

}
