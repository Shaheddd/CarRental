package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.Entity.Car;
import com.assignment.bangerandco.Entity.TransmissionType;
import com.assignment.bangerandco.Service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicle")
public class CarController {

    @Value("${uploadDirectory}")
    private String uploadFolder;

    @Autowired
    private CarService carService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = {"/", "home"})
    public String addCarPage() {
        return "AddVehicle";
    }

    @PostMapping("/saveCarDetails")
    public @ResponseBody
    ResponseEntity<?> createCarProduct(@RequestParam("model") String model,
                                       @RequestParam("brand") String brand,
                                       @RequestParam("transmissionType") TransmissionType transmissionType,
                                       @RequestParam("airConditioning") String airConditioning,
                                       @RequestParam("categoryType") String categoryType,
                                       @RequestParam("categoryPrice") double categoryPrice,
                                       @RequestParam("fuelType") String fuelType,
                                       @RequestParam("doors") int doors,
                                       @RequestParam("seats") int seats,
                                       Model modelName,
                                       HttpServletRequest httpServletRequest,
                                       final @RequestParam("image") MultipartFile multipartFile) {
        try {
            String uploadToDirectory = httpServletRequest.getServletContext().getRealPath(uploadFolder);
            logger.info("uploadToDirectory : " + uploadToDirectory);
            String fileName = multipartFile.getOriginalFilename();
            String filePath = Paths.get(uploadToDirectory, fileName).toString();
            logger.info("File Name : " + multipartFile.getOriginalFilename());

            if (fileName == null || fileName.contains("..")) {
                modelName.addAttribute("Invalid!", "Sorry, this File Name includes an invalid path sequence! \" +fileName");
                return new ResponseEntity<>("Sorry, this File Name includes an invalid path sequence!" + fileName, HttpStatus.BAD_REQUEST);
            }

            String[] models = model.split(",");
            String[] brands = brand.split(",");
            String[] categoryTypes = categoryType.split(",");
//            String[] categoryPrices = categoryPrice.split(",");
            String[] fuelTypes = fuelType.split(",");
//            String[] transmissionTypes = transmissionType.split(",");
            String[] airConditions = airConditioning.split(",");
            logger.info("Model : " + models[0] + " " + filePath);
            logger.info(("Brand : " + brands[0]));
            logger.info("Category Type : " + categoryTypes[0]);
            logger.info("Category Price : " + categoryPrice);
            logger.info("Fuel Type : " + fuelTypes[0]);
            logger.info("Transmission Type : " + transmissionType);
            logger.info("Air Conditioning : " + airConditions[0]);
            logger.info("Doors : " + doors);
            logger.info("Seats : " + seats);

            try {
                File directory = new File(uploadToDirectory);

                if (!directory.exists()) {
                    logger.info("Folder Created");
                    directory.mkdirs();
                }

                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                bufferedOutputStream.write(multipartFile.getBytes());
                bufferedOutputStream.close();
            } catch (Exception e) {
                logger.info("Error In Catch");
                e.printStackTrace();
            }

            byte[] carImageData = multipartFile.getBytes();
            Car optionalCar = new Car();
            optionalCar.setModel(models[0]);
            optionalCar.setBrand(brands[0]);
            optionalCar.setCategoryType(categoryTypes[0]);
            optionalCar.setCategoryPrice(categoryPrice);
            optionalCar.setFuelType(fuelTypes[0]);
            optionalCar.setTransmissionType(transmissionType);
            optionalCar.setAirConditioning(airConditions[0]);
            optionalCar.setDoors(doors);
            optionalCar.setSeats(seats);
            optionalCar.setImage(carImageData);
            carService.saveCarImage(optionalCar);

            logger.info("HttpStatus === " + new ResponseEntity<>(HttpStatus.OK));
            return new ResponseEntity<>("Car has been saved with the File!" + fileName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception : " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/image/display/{id}")
    @ResponseBody
    void showCarImage(@PathVariable("id") int id, HttpServletResponse httpServletResponse,
                      Optional<Car> optionalCar) throws ServletException, IOException {
        logger.info("Car ID : " + id);
        optionalCar = carService.getCarImageByID(id);
        httpServletResponse.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        httpServletResponse.getOutputStream().write(optionalCar.get().getImage());
        httpServletResponse.getOutputStream().close();

    }

    @GetMapping("/carImageDetails")
    String showCarDetails(@RequestParam("id") int id,
                          Optional<Car> optionalCar,
                          Model modelName) {
        try {
            logger.info("Car ID : " + id);

            if (id != 0) {
                optionalCar = carService.getCarImageByID(id);

                logger.info("car :: " + optionalCar);

                if (optionalCar.isPresent()) {
                    modelName.addAttribute("id", optionalCar.get().getId());
                    modelName.addAttribute("model", optionalCar.get().getModel());
                    modelName.addAttribute("brand", optionalCar.get().getBrand());
                    modelName.addAttribute("categoryType", optionalCar.get().getCategoryType());
                    modelName.addAttribute("categoryPrice", optionalCar.get().getCategoryPrice());
                    modelName.addAttribute("fuelType", optionalCar.get().getFuelType());
                    modelName.addAttribute("transmissionType", optionalCar.get().getTransmissionType());
                    modelName.addAttribute("airConditioning", optionalCar.get().getAirConditioning());
                    modelName.addAttribute("doors", optionalCar.get().getDoors());
                    modelName.addAttribute("seats", optionalCar.get().getSeats());
                    return "CarImageDetails";
                }

                return "redirect:/administrator/loadAdministratorHomepage";
            }

            return "redirect:/administrator/loadAdministratorHomepage";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/administrator/loadAdministratorHomepage";
        }
    }

    @GetMapping("/show")
    String show(Model modelName) {
        List<Car> images = carService.getAllActiveCarImages();
        modelName.addAttribute("images", images);
        return "CarImage2";
    }

    @GetMapping("/listAllSmallTownCars")
    String listAllSmallTownCars(Model modelName) {
        List<Car> listAllSmallTownCars = carService.getAllSmallTownCars();
        modelName.addAttribute("listAllSmallTownCars", listAllSmallTownCars);
        return "ListAllSmallTownCars";
    }

}

