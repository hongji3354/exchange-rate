package dev.jihun.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberFormatUtil {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    public static String format(double number) {
        return DECIMAL_FORMAT.format(number);
    }
}
