package com.pmm.pickmymenu_back.dto.request.rank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankMenuReq {

    /**
     * DAY, WEEK, MONTH, YEAR , ALL
     * 일별, 주별, 월별, 년별, 전체 순위
     */
    private String type;

    public LocalDateTime getTime() {
        LocalDateTime now = LocalDateTime.now();
        return switch (type.toUpperCase()) {
            case "DAY" -> now.minusDays(1);
            case "WEEK" -> now.minusDays(7);
            case "MONTH" -> now.minusMonths(1);
            case "YEAR" -> now.minusYears(1);
            case "ALL" -> null;
            default -> throw new RuntimeException("잘못된 파라미터 입니다.");
        };
    }
}
