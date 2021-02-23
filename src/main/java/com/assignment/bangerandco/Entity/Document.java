package com.assignment.bangerandco.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentID;

    private String documentName;
    private String documentType;

    @Lob
    private byte [] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_customerID")
    private Customer customer;

    public Document(String documentName, String documentType, byte [] data) {
        super();
        this.documentName = documentName;
        this.documentType = documentType;
        this.data = data;
    }
}
