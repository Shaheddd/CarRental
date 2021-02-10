package com.assignment.bangerandco.Repository;

import com.assignment.bangerandco.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT car from Car car where car.id = ?1")
    Car getById(long id);

    @Query("SELECT car from Car car where car.categoryType = 'Small Town Car'")
    List<Car> getSmallTownCars();

}