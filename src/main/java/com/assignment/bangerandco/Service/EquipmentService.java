package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.Equipment;
import com.assignment.bangerandco.Repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService implements EquipmentInterface {

    @Autowired
    EquipmentRepository equipmentRepository;

    @Override
    public void saveEquipment(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    @Override
    public List<Equipment> findAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment findEquipmentByID(Long equipmentID) {
        return equipmentRepository.findById(equipmentID).orElse(null);
    }


    @Override
    public Equipment getEquipmentByID(long equipmentID) {
        return equipmentRepository.getByEquipmentID(equipmentID);
    }
}
