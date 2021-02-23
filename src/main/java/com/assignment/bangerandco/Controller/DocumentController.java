package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.DataTransferObject.DocumentRegistration;
import com.assignment.bangerandco.Entity.Customer;
import com.assignment.bangerandco.Entity.Document;
import com.assignment.bangerandco.Repository.DocumentRepository;
import com.assignment.bangerandco.Service.CustomerService;
import com.assignment.bangerandco.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentService documentService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/listDocuments")
    public String getDocument(Model model) {
        List<Document> documentList = documentService.getAllDocuments();
        model.addAttribute("documents", documentList);
        return "ListDocuments";
    }

    @PostMapping("/uploadDocument")
    public String uploadDocument(@RequestParam("files") MultipartFile[] multipartFile,@ModelAttribute("customer") Customer customer) {
        for(MultipartFile multipartFiles : multipartFile) {
            documentService.saveDocument(multipartFiles,customer);
        }

        return "redirect:/administrator/loadAdministratorHomepage";
    }

    @GetMapping("/downloadDocument/{documentID}")
    public ResponseEntity<ByteArrayResource> downloadDocument(@PathVariable Integer documentID) {
        Document document = documentService.getDocument(documentID).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getDocumentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "Attachment : Document Name =\""
                + document.getDocumentName() + "\"")
                .body(new ByteArrayResource(document.getData()));
    }

    @GetMapping("/addDocument/{id}")
    public String addDocument(@PathVariable(value = "id") int customerID, Model model) {

        Customer customer = customerService.getCustomerByID(customerID);
        DocumentRegistration documentRegistration = new DocumentRegistration();
        documentRegistration.setCustomer(customer);

        model.addAttribute("document", documentRegistration);
        model.addAttribute("customer", customer);
        return "AddDocumentToCustomer";

    }
}
