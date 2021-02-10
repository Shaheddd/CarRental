package com.assignment.bangerandco.Repository;

import com.assignment.bangerandco.Entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @Query("SELECT equipment from Equipment equipment where equipment.equipmentID = ?1")
    Equipment getByEquipmentID(long equipmentID);
}
