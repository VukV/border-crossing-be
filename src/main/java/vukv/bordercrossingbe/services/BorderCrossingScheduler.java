package vukv.bordercrossingbe.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BorderCrossingScheduler {

    private final BorderCrossingService borderCrossingService;

    @Scheduled(cron = "0 0 12 * * *") // 12 PM
    public void deleteIncompleteCrossings() {
        log.info("Cleaning up incomplete crossings...");
        borderCrossingService.deleteIncompleteCrossings();
        log.info("Cleaned up incomplete crossings.");
    }

}
