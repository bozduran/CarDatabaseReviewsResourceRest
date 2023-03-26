package com.bozntouran.car_database_reviews_rest.repositories;

import com.bozntouran.car_database_reviews_rest.model.CarBrandCSV;
import com.bozntouran.car_database_reviews_rest.services.CarBrandCSVImpl;
import com.bozntouran.car_database_reviews_rest.services.CarBrandCSVService;
import com.bozntouran.car_database_reviews_rest.services.CarBrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import static org.assertj.core.api.Assertions.assertThat;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@SpringBootTest
public class CarBrandCSVImplTest {
    @Autowired
    private CarBrandCSVService carBrandCSVService;

    @Test
    public void testLoadedSize() throws FileNotFoundException {

        File carBrandsCSV = ResourceUtils.getFile("classpath:csvdata/car_brands.csv");
        List<CarBrandCSV> carBrandCSVList = carBrandCSVService.conevertCSV(carBrandsCSV);

        assertThat(carBrandCSVList.size()).isEqualTo(74);
    }
}
