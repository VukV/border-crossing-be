package vukv.bordercrossingbe.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import vukv.bordercrossingbe.TestAuthConfig;
import vukv.bordercrossingbe.TestcontainersConfiguration;
import vukv.bordercrossingbe.domain.dtos.bordercrossing.BorderCrossingAnalyticsDto;
import vukv.bordercrossingbe.domain.entities.border.Border;
import vukv.bordercrossingbe.domain.entities.bordercrossing.BorderCrossing;
import vukv.bordercrossingbe.domain.entities.user.User;
import vukv.bordercrossingbe.repositories.BorderCrossingRepository;
import vukv.bordercrossingbe.repositories.BorderRepository;
import vukv.bordercrossingbe.repositories.UserRepository;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@Sql(value = {"/sql/auth.sql", "/sql/border.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class BorderCrossingAnalyticsServiceTest extends TestAuthConfig {

    @Autowired
    private BorderCrossingAnalyticsService borderCrossingAnalyticsService;
    @Autowired
    private BorderRepository borderRepository;
    @Autowired
    private BorderCrossingRepository borderCrossingRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetBorderCrossingAnalytics() {
        setUpAnalyticsData();
        BorderCrossingAnalyticsDto analytics = borderCrossingAnalyticsService.getAnalyticsByBorderId(
                UUID.fromString("10617af4-60d5-4bf6-997e-8ee14efa4cef"), "Europe/Belgrade");

        assertNotNull(analytics);
        assertNotEquals(0, analytics.getAverageToday());
        assertNotEquals(0, analytics.getAverageWeek());
        assertNotEquals(0, analytics.getAverageMonth());
        assertNotEquals(0, analytics.getAverageCurrentHour());
        assertFalse(analytics.getAverageByHour().isEmpty());
    }

    private void setUpAnalyticsData() {
        createBorderCrossing(Instant.now().minusSeconds(2000), Instant.now().minusSeconds(1000));
        createBorderCrossing(Instant.now().minusSeconds(3000), Instant.now().minusSeconds(1800));
        createBorderCrossing(Instant.now().minusSeconds(1500), Instant.now().minusSeconds(300));
        createBorderCrossing(Instant.now().minusSeconds(800), Instant.now());
        createBorderCrossing(Instant.now().minusSeconds(8000), Instant.now().minusSeconds(6700));
    }

    private void createBorderCrossing(Instant arrivalTimestamp, Instant crossingTimestamp) {
        Border border = borderRepository.getReferenceById(UUID.fromString("10617af4-60d5-4bf6-997e-8ee14efa4cef"));
        User user = userRepository.getReferenceById(UUID.fromString("e49fcab5-d45b-4556-9d91-14e58177fea6"));

        BorderCrossing borderCrossing = BorderCrossing.builder()
                .arrivalTimestamp(arrivalTimestamp)
                .crossingTimestamp(crossingTimestamp)
                .border(border)
                .createdBy(user)
                .build();
        borderCrossing.setDuration();

        borderCrossingRepository.save(borderCrossing);
    }

}
