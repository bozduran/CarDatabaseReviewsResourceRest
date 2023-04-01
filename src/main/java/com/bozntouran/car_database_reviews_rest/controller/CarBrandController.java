package com.bozntouran.car_database_reviews_rest.controller;

import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.services.CarBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CarBrandController {
    private static final String CAR_BRAND = "/api/brand";
    private static final String CAR_BRAND_ID = CAR_BRAND + "/{carBrandId}";

    private final CarBrandService carBrandService;

    @PatchMapping(value = CAR_BRAND_ID)
    public ResponseEntity patchCarBrand(@PathVariable("carBrandId") UUID carBrandId, @RequestBody CarBrandDTO carBrandDTO){

        carBrandService.patchCarBrandByID(carBrandId,carBrandDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PutMapping(CAR_BRAND_ID)
    public ResponseEntity updatedCarBrand(@PathVariable("carBrandId") UUID  carBrandId, @Validated @RequestBody CarBrandDTO carBrandDTO){

        if ( carBrandService.updateCarBRandByID(carBrandId, carBrandDTO).isEmpty() ){
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    @DeleteMapping(CAR_BRAND_ID)
    public ResponseEntity deleteCarBrandByID(@PathVariable UUID carBrandId){
        if (!carBrandService.deleteCarBrandByID(carBrandId)){
            throw  new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PostMapping(CAR_BRAND)
    public ResponseEntity handlePost(@Validated @RequestBody CarBrandDTO carBrandDTO){

        CarBrandDTO savedCarBrand = carBrandService.saveNewCarBrand(carBrandDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CAR_BRAND + "/"
                + savedCarBrand.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @GetMapping(value = CAR_BRAND)
    public Page<CarBrandDTO> getAllCars(@RequestParam(required = false) String brandName,
                                        @RequestParam(required = false) String countryOfOrigin,
                                        @RequestParam(required = false) Integer yearOfFoundation,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize){
        System.out.println(countryOfOrigin);
        return carBrandService.getAllBrands(brandName, countryOfOrigin, yearOfFoundation, pageNumber, pageSize);
    }

    @GetMapping(value = CAR_BRAND_ID)
    public CarBrandDTO getCarBrandById(@PathVariable("carBrandId") UUID carBrandId){
        return carBrandService.getCarBrandByID(carBrandId).orElseThrow(NotFoundException::new);
    }
/*
    @GetMapping(value = CAR_BRAND + "/{carBrandName}")
    public CarBrandDTO getCarBrandByName(@PathVariable String carBrandName){
        return ;
    }
*/
}

