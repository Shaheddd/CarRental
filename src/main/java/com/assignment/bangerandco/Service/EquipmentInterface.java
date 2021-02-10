package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.Equipment;

import java.util.List;

public interface EquipmentInterface {
    void saveEquipment(Equipment equipment);

    List<Equipment> findAllEquipment();

    Equipment findEquipmentByID(Long equipmentID);

    Equipment getEquipmentByID(long equipmentID);
}
