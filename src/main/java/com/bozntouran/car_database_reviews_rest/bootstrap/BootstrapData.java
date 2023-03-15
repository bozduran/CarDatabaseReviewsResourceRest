package com.bozntouran.car_database_reviews_rest.bootstrap;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.repositories.CarBrandRepository;
import com.bozntouran.car_database_reviews_rest.repositories.CarModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;

@AllArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {


    private CarBrandRepository carBrandRepository;
    private CarModelRepository carModelRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Data bootstrapping.");
        CarBrand ferrari = CarBrand.builder()
                .brandName("Ferrari")
                .countryOfOrigin("Italy")
                .creationYear(1943)
                .build();
        CarBrand ford = CarBrand.builder()
                .brandName("Ford")
                .countryOfOrigin("United States")
                .creationYear(1903)
                .build();


        CarModel enzo = CarModel.builder()
                .modelName("Enzo")
                .yearOfProduction(2002)
                .build();
        CarModel gt = CarModel.builder()
                .modelName("GT")
                .yearOfProduction(2002)
                .build();

        carModelRepository.save(enzo);
        carModelRepository.save(gt);



        CarBrand savedFerrari = carBrandRepository.save(ferrari);
        CarBrand savedFord = carBrandRepository.save(ford);



        System.out.println("Data bootstrapping end.");


    }

}
