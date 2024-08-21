package vukv.bordercrossingbe.domain.dtos.bordercrossing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vukv.bordercrossingbe.domain.dtos.border.BorderDto;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorderCrossingAnalyticsDto {

    private long averageToday;
    private long averageWeek;
    private long averageMonth;

    private long averageCurrentHour;

    // TODO average by hour (last 6 hours)

    private BorderDto border;

}
