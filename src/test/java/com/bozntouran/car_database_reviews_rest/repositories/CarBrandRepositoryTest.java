package com.bozntouran.car_database_reviews_rest.repositories;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarBrandRepositoryTest {

    @Autowired
    CarBrandRepository carBrandRepository;

    @BeforeEach
    void setUp() {
    }

    @Rollback
    @Transactional
    @Test
    void saveCarBrand(){
        CarBrand savedCarModel = carBrandRepository.save(CarBrand.builder()
                        .brandName("Something")
                .build());

        assertThat(savedCarModel.getId()).isNotNull();

    }

    @Test
    void getCarBrandById() {
    }

    @Test
    void existsById() {
    }

    @Test
    void getCarBrandByBrandName() {
    }
}