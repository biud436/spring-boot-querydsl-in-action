package com.biud436.rest;

import io.jsonwebtoken.lang.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void convertTimeToLong() {
        long MINUTE = 60L;
        long HOUR = 60L * 60L;

        String time = "14d";
        Long result = 0L;

        if (!Strings.hasText(time)) {
            time = time.replaceAll("[^0-9]", "");
            result = Long.parseLong(time);

            System.out.println(result);
            return;
        }

        if (time.contains("m")) {
            result = Long.parseLong(time.replace("m", "")) * MINUTE;
        } else if (time.contains("h")) {
            result = Long.parseLong(time.replace("h", "")) * HOUR;
        } else if (time.contains("d")) {
            result = Long.parseLong(time.replace("d", "")) * HOUR * 24L;
        } else if (time.contains("w")) {
            result = Long.parseLong(time.replace("w", "")) * HOUR * 24L * 7L;
        } else if (time.contains("M")) {
            result = Long.parseLong(time.replace("M", "")) * HOUR * 24L * 30L;
        } else if (time.contains("y")) {
            result = Long.parseLong(time.replace("y", "")) * HOUR * 24L * 365L;
        }

        System.out.println(result);
    }

}
