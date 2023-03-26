package com.bozntouran.car_database_reviews_rest.controller;

import com.bozntouran.car_database_reviews_rest.mappers.CarBrandMapper;
import com.bozntouran.car_database_reviews_rest.mappers.CarModelMapper;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import com.bozntouran.car_database_reviews_rest.services.CarBrandService;
import com.bozntouran.car_database_reviews_rest.services.CarModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequiredArgsConstructor
public class CarModelController {
    private static final String CAR_MODEL = "/api/car-model";
    private static final String CAR_MODEL_ID = "/api/car-model/{carModelID}";

    private final CarModelService carModelService;

    private final CarBrandService carBrandService;

    @PutMapping(value = CAR_MODEL + "/{carBrandName}")
    public ResponseEntity addNewModel(@PathVariable("carBrandName")String carBrandName,
                                      @Validated @RequestBody CarModelDTO carModelDto){

        CarBrandDTO carBrandDTO = carBrandService.getAllBrands(
                carBrandName, null, null).get(0);
        var savedCarBrand = carBrandService.updateCarBRandByID(carBrandDTO.getId(), carBrandDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping(value = CAR_MODEL_ID)
    public CarModelDTO getCarModelByID(@PathVariable("carModelID")UUID carModelID){
        return carModelService.getCarModelByID(carModelID).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = CAR_MODEL)
    public List<CarModelDTO> getAllCarModels(){
        log.info("getall");
        return carModelService.getAllModels();
    }

}
