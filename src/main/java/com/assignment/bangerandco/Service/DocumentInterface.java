package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentInterface {

    Document saveDocument(MultipartFile multipartFile, Customer customer);

    Optional<Document> getDocument(Integer multipartFileID);

    List<Document> getAllDocuments();
}
