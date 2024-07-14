package vukv.bordercrossingbe.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import vukv.bordercrossingbe.TestcontainersConfiguration;
import vukv.bordercrossingbe.domain.dtos.border.BorderCreateRequest;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;
import vukv.bordercrossingbe.domain.dtos.border.BorderFilterRequest;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.entities.border.Country;
import vukv.bordercrossingbe.domain.entities.border.Location;
import vukv.bordercrossingbe.exception.exceptions.NotFoundException;
import vukv.bordercrossingbe.repositories.BorderRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class BorderServiceTest {

    @Autowired
    private BorderService borderService;
    @Autowired
    private BorderRepository borderRepository;

    @Test
    void testGetBorderById() {
        UUID borderId = createBorder().getId();
        BorderDto borderDto = borderService.getBorderById(borderId);
        assertNotNull(borderDto);
        assertEquals("Test Border", borderDto.getName());
    }

    @Test
    void testGetBorderByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> borderService.getBorderById(nonExistentId));
    }

    @Test
    void testGetAllBorders() {
        createBorder();
        createBorder();
        BorderFilterRequest filterRequest = BorderFilterRequest.builder()
                .name("test")
                .build();

        Page<BorderDto> borders = borderService.getAllBorders(filterRequest);
        assertNotNull(borders);
        assertTrue(borders.hasContent());
        assertEquals(2, borders.getTotalElements());
    }

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

    @Test
    void testCreateBorder() {
        BorderCreateRequest createRequest = BorderCreateRequest.builder()
                .name("New Border")
                .countryFrom(Country.SRB)
                .countryTo(Country.MNE)
                .location(Location.builder()
                        .latitude(10)
                        .longitude(20)
                        .build())
                .build();

        BorderDto borderDto = borderService.createBorder(createRequest);
        assertNotNull(borderDto);
        assertEquals("New Border", borderDto.getName());
        assertEquals(Country.SRB, borderDto.getCountryFrom());
        assertEquals(Country.MNE, borderDto.getCountryTo());
    }

    @Test
    void testDeleteBorder() {
        UUID borderId = createBorder().getId();

        Border borderBeforeDeletion = borderRepository.findById(borderId).orElse(null);
        assertNotNull(borderBeforeDeletion);

        borderService.deleteBorder(borderId);

        Border borderAfterDeletion = borderRepository.findById(borderId).orElse(null);
        assertNull(borderAfterDeletion);
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
