package vukv.bordercrossingbe.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import vukv.bordercrossingbe.TestcontainersConfiguration;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.entities.border.Country;
import vukv.bordercrossingbe.domain.entities.border.Location;
import vukv.bordercrossingbe.repositories.BorderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class BorderServiceTest {

    @Autowired
    private BorderService borderService;
    @Autowired
    private BorderRepository borderRepository;

    @Test
    public void testGetBordersByDistance() {
        Border border = createBorder();

        double userLatitude = 40.6892;
        double userLongitude = 74.0445;
        int distance = 6000;

        List<BorderDto> borders = borderService.getAllBordersByDistance(distance, userLatitude, userLongitude);

        assertNotNull(borders);
        assertFalse(borders.isEmpty());
        assertEquals(border.getName(), borders.get(0).getName());
    }

    private Border createBorder() {
        Border border = Border.builder()
                .name("Test Border")
                .countryFrom(Country.SRB)
                .countryTo(Country.MNE)
                .location(Location.builder()
                        .latitude(51.5007)
                        .longitude(0.1246)
                        .build())
                .build();

        return borderRepository.save(border);
    }

}
