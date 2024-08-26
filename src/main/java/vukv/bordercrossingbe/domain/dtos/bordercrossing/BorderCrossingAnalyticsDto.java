package vukv.bordercrossingbe.domain.dtos.bordercrossing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorderCrossingAnalyticsDto {

    private long averageToday;
    private long averageWeek;
    private long averageMonth;

    private long averageCurrentHour;
    private List<AverageByHour> averageByHour;

    private BorderDto border;

    public interface AverageByHour {
        int getHourOfDay();
        long getAverageDuration();
    }

}
