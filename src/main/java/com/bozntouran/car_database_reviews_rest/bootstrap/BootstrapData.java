package com.bozntouran.car_database_reviews_rest.bootstrap;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.model.CarBrandCSV;
import com.bozntouran.car_database_reviews_rest.model.CarModelCSV;
import com.bozntouran.car_database_reviews_rest.repositories.CarBrandRepository;
import com.bozntouran.car_database_reviews_rest.repositories.CarModelRepository;
import com.bozntouran.car_database_reviews_rest.services.CarBrandCSVService;
import com.bozntouran.car_database_reviews_rest.services.CarModelCSVService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@AllArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {


    private CarBrandRepository carBrandRepository;
    private CarModelRepository carModelRepository;

    private CarBrandCSVService carBrandCSVService;
    private CarModelCSVService carModelCSVService;
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Data bootstrapping.");
        if (carBrandRepository.count() == 0)
            loadCarBrandDataFromCsv();
        if(carModelRepository.count() == 0)
            loadCarModelDataFromCsv();

        System.out.println("Data bootstrapping end.");


    }

    @Transactional
    public void loadCarModelDataFromCsv(){
        File carModelCSVFile = null;
        try {
            carModelCSVFile = ResourceUtils.getFile("classpath:csvdata/vehicles.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<CarModelCSV> carModelCSVList = carModelCSVService.conevertCSV(carModelCSVFile);
/*
        carModelCSVList.forEach(carModelCSV -> {
            carModelRepository.save(CarModel
                    .builder()
                            .modelName(carModelCSV.getModel())
                            .carType(carModelCSV.getVClass())
                            .fuelType(carModelCSV.getFuelType())
                            .yearOfProduction(carModelCSV.getYear())
                            .drive(carModelCSV.getDrive())
                            .transmision(carModelCSV.getTrany())
                    .build());
            if (carModelRepository.count() == 100)
                return;
        } );*/


        int count = 0;
        for (CarModelCSV carModelCSV : carModelCSVList) {

            carModelRepository.save(CarModel
                    .builder()
                    .modelName(carModelCSV.getModel())
                    .carType(carModelCSV.getVClass())
                    .fuelType(carModelCSV.getFuelType())
                    .yearOfProduction(carModelCSV.getYear())
                    .drive(carModelCSV.getDrive())
                    .transmision(carModelCSV.getTrany())
                    .build());
            count++;
            if (count == 100) {
                System.out.println(100);
                break;
            }
        }
    }

    @Transactional
    public void loadCarBrandDataFromCsv(){
        File carBrandsCSVFile = null;
        try {
            carBrandsCSVFile = ResourceUtils.getFile("classpath:csvdata/car_brands.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<CarBrandCSV> carBrandCSVList = carBrandCSVService.conevertCSV(carBrandsCSVFile);


        carBrandCSVList.forEach(carBrandCSV -> {
            carBrandRepository.save(CarBrand.builder()
                            .brandName(carBrandCSV.getCarbrand())
                            .countryOfOrigin(carBrandCSV.getCountyoforigin())
                            .yearOfFoundation(carBrandCSV.getYearoffoundation())
                    .build());
        });
    }

}
