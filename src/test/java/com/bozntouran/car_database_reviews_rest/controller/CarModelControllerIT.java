package com.bozntouran.car_database_reviews_rest.controller;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.mappers.CarBrandMapper;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.repositories.CarBrandRepository;
import com.bozntouran.car_database_reviews_rest.services.CarBrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

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
                .yearOfFoundation(1990)
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
        Page<CarBrandDTO> carBrands = carBrandService.getAllBrands(null, null, null, 1, 10);
        assertThat(carBrands.getContent().size()).isEqualTo(10);

    }

    @Rollback
    @Transactional
    @Test
    public void testForEmptyRepository(){
        carBrandRepository.deleteAll();
        Page<CarBrandDTO> carBrands = carBrandService.getAllBrands(null, null, null, 1, 10);
        assertThat(0).isEqualTo(carBrands.getContent().size());
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteByID(){
        CarBrandDTO carBrandDTO = carBrandController.getAllCars(null,null,null,null,null).getContent().get(0);

        carBrandController.deleteCarBrandByID(carBrandDTO.getId());

        assertThrows(NotFoundException.class,()->{
            carBrandController.getCarBrandById(carBrandDTO.getId());
        });


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

        System.out.println(carBrand.getId());
        ResponseEntity responseEntity = carBrandController.updatedCarBrand(carBrand.getId(),carBrandDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        CarBrand carBrand1 = carBrandRepository.getCarBrandById(carBrand.getId());
        System.out.println(carBrand1.getId());
        assertThat(carBrand1.getBrandName()).isEqualTo(newName);


    }
}
