package com.bozntouran.car_database_reviews_rest.repositories;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarModelRepositoryTest {

    @Autowired
    private CarModelRepository carModelRepository;

    @Rollback
    @Transactional
    @Test
    void testSaveCarModel(){
        CarModel savedCarModel = carModelRepository.save(
                CarModel.builder()
                        .modelName("Some new model")
                        .build()
        );

        assertThat(savedCarModel).isNotNull();
    }
}