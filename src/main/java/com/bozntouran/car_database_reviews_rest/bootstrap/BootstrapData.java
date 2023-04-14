package com.bozntouran.car_database_reviews_rest.bootstrap;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.mappers.CarBrandMapper;
import com.bozntouran.car_database_reviews_rest.mappers.CarModelMapper;
import com.bozntouran.car_database_reviews_rest.model.CarBrandCSV;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.model.CarModelCSV;
import com.bozntouran.car_database_reviews_rest.repositories.CarBrandRepository;
import com.bozntouran.car_database_reviews_rest.repositories.CarModelRepository;
import com.bozntouran.car_database_reviews_rest.services.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@AllArgsConstructor
@Component
@Slf4j
public class BootstrapData implements CommandLineRunner {


    private final CarBrandRepository carBrandRepository;
    private final CarModelRepository carModelRepository;

    private final CarModelService carModelService;
    private final CarBrandService carBrandService;

    private final CarBrandMapper carBrandMapper;

    private final CarBrandCSVService carBrandCSVService;
    private final CarModelCSVService carModelCSVService;
    @Override
    public void run(String... args) throws Exception {

        log.info("Data bootstrapping.");
        if (carBrandRepository.count() == 0)
            loadCarBrandDataFromCsv();
        if(carModelRepository.count() == 0)
            loadCarModelDataFromCsv();

        log.info("Data bootstrapping end.");


    }

    @Transactional
    public void loadCarModelDataFromCsv(){
        log.info("loadCarModelDataFromCsv");

        File carModelCSVFile = null;
        try {
            carModelCSVFile = ResourceUtils.getFile("classpath:csvdata/vehicles.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<CarModelCSV> carModelCSVList = carModelCSVService.conevertCSV(carModelCSVFile);

        int count = 0;
        for (CarModelCSV carModelCSV : carModelCSVList) {


            Page<CarBrandDTO> carBrandDTOPage = carBrandService.getAllBrands(
                    carModelCSV.getMake()
                    ,null,null,null,null
            );


            if ( carBrandDTOPage.isEmpty()){
                log.debug(carModelCSV.getMake());
            }else{
                CarBrand carBrand = carBrandMapper.carBrandDtoToCarBrand(
                        carBrandDTOPage.getContent().get(0));

                carModelRepository.save(CarModel
                        .builder()
                        .modelName(carModelCSV.getModel())
                        .carType(carModelCSV.getVClass())
                        .fuelType(carModelCSV.getFuelType())
                        .yearOfProduction(carModelCSV.getYear())
                        .drive(carModelCSV.getDrive())
                        .transmision(carModelCSV.getTrany())
                        .carBrand(carBrand)
                        .build());
                count++;
                if (count == 500) {
                    log.info("Inserted 100 models.");
                    break;
                }
            }


        }
        carModelRepository.flush();
    }

    @Transactional
    public void loadCarBrandDataFromCsv(){
        log.info("loadCarBrandDataFromCsv");

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
