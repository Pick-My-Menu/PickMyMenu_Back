package com.pmm.pickmymenu_back.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomUtil {

    public static String formatter(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

}
