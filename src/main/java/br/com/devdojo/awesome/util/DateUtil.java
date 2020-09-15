package br.com.devdojo.awesome.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    public  String formatLocalDataTimeToDatabaseStyle(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
