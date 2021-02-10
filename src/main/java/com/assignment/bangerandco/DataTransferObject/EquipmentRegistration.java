package com.assignment.bangerandco.DataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRegistration {

    private long equipmentID;

    private String equipmentName;
    private String equipmentDescription;
    private Double price;
}
