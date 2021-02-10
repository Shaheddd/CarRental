package com.assignment.bangerandco.Controller;

import com.assignment.bangerandco.Entity.Car;
import com.assignment.bangerandco.Entity.WebScraping;
import com.assignment.bangerandco.Service.CarService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/webScrape")
public class WebScrapingController {

    @Autowired
    CarService carService;

    @GetMapping("/getData")
    public String getAllWebScrapingData(Model model) {
        List<WebScraping> webScrapingList = new ArrayList<>();
        List<Car> carList = carService.getAllVehicles();

        final String url = "https://www.malkey.lk/rates/self-drive-rates.html";

        try {
            final Document document = Jsoup.connect(url).get();

            for(Element row: document.select("table.table.selfdriverates tr")) {

                WebScraping webScraping = new WebScraping();

                final String vehicleName= row.select("td.text-left.percent-40").text();
                if(!vehicleName.contentEquals("")) {
                    webScraping.setVehicleName(vehicleName);
                }
                final String rates = row.select("td.text-center.percent-22").text();
                if(!rates.contentEquals("")) {

                    String[] priceList = rates.split(" ");

                    webScraping.setPricePerMonth(priceList[0]);
                    webScraping.setPricePerWeek(priceList[1]);
                    webScraping.setDailyMileage(priceList[2]);

                }

                if(webScraping.getVehicleName()!=null) {
                    webScrapingList.add(webScraping);
                }

            }

        }catch(Exception e) {
            e.getMessage();
        }


        model.addAttribute("webScraping", webScrapingList);
        model.addAttribute("allVehicles", carList);
        return "WebScrapingVehicleList";
    }
}
