package com.github.alwaysdarkk.missions.common.util;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;
import java.util.List;

@UtilityClass
public class NumberUtil {

    private final List<String> SUFFIXES = List.of(
            "", "k", "M", "B", "T", "Q", "QQ", "S", "SS", "OC", "N", "D", "UN", "DD", "TR", "QT", "QN", "SD", "SPD",
            "OD", "ND", "VG", "UVG", "DVG", "TVG", "QTV", "QNV", "SEV", "SPV", "OVG", "NVG", "TG");

    private final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#.##");

    public String format(double number) {
        if (isInvalid(number)) {
            return "0";
        }

        int index = 0;
        double tmp;

        while ((tmp = number / 1000) >= 1) {
            if (index + 1 == SUFFIXES.size()) {
                break;
            }

            number = tmp;
            ++index;
        }

        return NUMBER_FORMAT.format(number) + SUFFIXES.get(index);
    }

    public boolean isInvalid(double number) {
        return number <= 0 || Double.isNaN(number) || Double.isInfinite(number);
    }
}