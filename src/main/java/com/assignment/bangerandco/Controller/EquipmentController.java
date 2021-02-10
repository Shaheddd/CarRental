package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.Entity.Equipment;
import com.assignment.bangerandco.Service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    @GetMapping("/loadEquipmentForm")
    public String loadEquipmentForm(Model model) {
        model.addAttribute("equipment", new Equipment());
        return "AddEquipment";
    }

    @PostMapping("/addEquipment")
    public String addEquipment(@ModelAttribute("equipment") Equipment equipment) {
        Equipment addEquipment = new Equipment();
        addEquipment.setEquipmentID(equipment.getEquipmentID());
        addEquipment.setEquipmentName(equipment.getEquipmentName());
        addEquipment.setEquipmentDescription(equipment.getEquipmentDescription());
        addEquipment.setPrice(equipment.getPrice());
        equipmentService.saveEquipment(addEquipment);

        return "redirect:/administrator/loadAdministratorHomepage";
    }

    @GetMapping("/listEquipment")
    public String listAllEquipment(Model model) {
        model.addAttribute("equipments", equipmentService.findAllEquipment());
        return "AddBooking";
    }

    @GetMapping("/listEquipments")
    public String listEquipments(Model model) {
        model.addAttribute("equipments", equipmentService.findAllEquipment());
        return "ListEquipment";
    }
}
