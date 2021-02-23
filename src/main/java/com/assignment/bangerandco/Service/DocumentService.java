package com.assignment.bangerandco.Service;

import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.Document;
import com.assignment.bangerandco.Repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService implements DocumentInterface {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    CustomerService customerService;

    @Override
    public Document saveDocument(MultipartFile multipartFile, Customer customer) {
        String documentName = multipartFile.getOriginalFilename();
        try {
            Document document = new Document(documentName, multipartFile.getContentType(), multipartFile.getBytes());
            document.setCustomer(customerService.getCustomerByID(customer.getCustomerID()));
            return documentRepository.save(document);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Document> getDocument(Integer multipartFileID) {
        return documentRepository.findById(multipartFileID);
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}
