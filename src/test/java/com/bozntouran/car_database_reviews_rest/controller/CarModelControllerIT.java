package com.bozntouran.car_database_reviews_rest.controller;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.mappers.CarBrandMapper;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import com.bozntouran.car_database_reviews_rest.repositories.CarBrandRepository;
import com.bozntouran.car_database_reviews_rest.services.CarBrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.InstanceOfAssertFactories.predicate;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class CarModelControllerIT {

    @Autowired
    private CarBrandRepository carBrandRepository;
    @Autowired
    private CarBrandController carBrandController;

    @Autowired
    private CarBrandService carBrandService;

    @Autowired
    private CarBrandMapper carBrandMapper;
    @Rollback
    @Transactional
    @Test
    public void testSaveNewCarBrand(){
        CarBrandDTO carBrandDTO = CarBrandDTO.builder()
                .brandName("Fiat")
                .countryOfOrigin("Italy")
                .creationYear(1990)
                .build();

        ResponseEntity responseEntity = carBrandController.handlePost(carBrandDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID savedUUID = UUID.fromString(locationUUID[3]);

        CarBrand carModel = carBrandRepository.getCarBrandById(savedUUID);
        assertThat(carModel).isNotNull();

    }

    @Transactional
    @Rollback
    @Test
    public void failSave(){

    }

    @Test
    public void getAllBrands(){
        List<CarBrand> carBrandsEntities = carBrandRepository.findAll();
        List<CarBrandDTO> carBrands = carBrandService.getAllBrands();
        assertThat(carBrands.size()).isEqualTo(carBrandsEntities.size());

    }

    @Rollback
    @Transactional
    @Test
    public void testForEmptyRepository(){
        carBrandRepository.deleteAll();
        List<CarBrandDTO> carBrands = carBrandService.getAllBrands();
        assertThat(0).isEqualTo(carBrands.size());
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteByID(){

    }

    @Test
    void testDeleteByIDNotFound() {
        assertThrows(NotFoundException.class, () -> {
            carBrandController.deleteCarBrandByID(UUID.randomUUID());
        });
    }

    @Test
    void testGetBrandByID(){
        CarBrand carBrand = carBrandRepository.findAll().get(0);

        CarBrandDTO carBrandlDTO = carBrandController.getCarBrandById(carBrand.getId());
        assertThat(carBrandlDTO).isNotNull();
    }

    @Test
    void testGetBrandByRandomID() {
        assertThrows(NotFoundException.class, () -> {
            carBrandController.getCarBrandById(UUID.randomUUID());
        });
    }

    @Test
    void testUpdateByID() {

        CarBrand carBrand = carBrandRepository.findAll().get(0);
        CarBrandDTO carBrandDTO = carBrandMapper.carBrandToCarBrandDto(carBrand);
        final String newName = "New Brand";
        carBrandDTO.setId(null);
        carBrandDTO.setBrandName(newName);
        carBrandDTO.setVersion(null);


        ResponseEntity responseEntity = carBrandController.updatedCarBrand(carBrand.getId(),carBrandDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        //TODO : Check
        CarBrand carBrand1 = carBrandRepository.getCarBrandById(carBrand.getId());
        assertThat(carBrand1.getBrandName()).isEqualTo(newName);


    }
}
