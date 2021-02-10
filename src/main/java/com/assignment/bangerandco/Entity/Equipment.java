package com.assignment.bangerandco.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipment")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Equipment {

//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long equipmentID;

    private String equipmentName;
    private String equipmentDescription;
    private Double price;

    @ManyToMany(mappedBy = "equipmentList")
    private List<Booking> bookingList;

}