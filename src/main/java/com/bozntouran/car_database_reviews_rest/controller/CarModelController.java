package com.bozntouran.car_database_reviews_rest.controller;

import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import com.bozntouran.car_database_reviews_rest.services.CarBrandService;
import com.bozntouran.car_database_reviews_rest.services.CarModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CarModelController {
    private static final String CAR_MODEL = "/api/car-model";
    private static final String CAR_MODEL_ID = "/api/car-model/{carModelID}";

    private final CarModelService carModelService;

    private final CarBrandService carBrandService;

    @PatchMapping(value = CAR_MODEL_ID)
    public ResponseEntity patchCarModel(@PathVariable("carModelID") UUID carModelID,@RequestBody CarModelDTO carModelDTO ){
        carModelService.pattchCarModelByID(carModelID,carModelDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = CAR_MODEL + "/{carBrandName}")
    public ResponseEntity addNewModel(@PathVariable("carBrandName")String carBrandName,
                                      @Validated @RequestBody CarModelDTO carModelDto){

        CarBrandDTO carBrandDTO = carBrandService.getAllBrands(
                carBrandName, null, null, 1, 10).getContent().get(0);
        carBrandService.updateCarBRandByID(carBrandDTO.getId(), carBrandDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(CAR_MODEL_ID)
    public ResponseEntity deleteCarModelByID(@PathVariable UUID carModelID){
        if (!carModelService.deleteCarModelByID(carModelID)){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PostMapping(CAR_MODEL)
    public ResponseEntity handlePost(@Validated @RequestBody CarModelDTO carModelDTO){

        CarModelDTO savedCarModel = carModelService.saveCarModel(carModelDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",CAR_MODEL+"/"
                + savedCarModel.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }




    @GetMapping(value = CAR_MODEL)
    public Page<CarModelDTO> getAllCarModels(@RequestParam(required = false) String modelName,
                                        @RequestParam(required = false) String drive,
                                        @RequestParam(required = false) String fuelType,
                                        @RequestParam(required = false) String transmision,
                                        @RequestParam(required = false) String carType,
                                        @RequestParam(required = false) Integer yearOfProduction,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize){

        return carModelService.getAllCarModels(modelName,
                drive,
                fuelType,
                transmision,
                carType,
                yearOfProduction,
                pageNumber,pageSize);
    }

    @GetMapping(value = CAR_MODEL_ID)
    public CarModelDTO getCarModelByID(@PathVariable("carModelID")UUID carModelID){
        return carModelService.getCarModelByID(carModelID).orElseThrow(NotFoundException::new);
    }

}
