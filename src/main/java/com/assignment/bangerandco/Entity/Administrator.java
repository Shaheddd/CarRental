package com.assignment.bangerandco.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int administratorID;
    private String administratorFirstName;
    private String administratorLastName;
    private String administratorAddress;
    private String administratorPhoneNumber;
    private int userID;
}
