package com.github.alwaysdarkk.missions.common.util;

import com.google.common.base.Strings;
import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;

@UtilityClass
public class ProgressBarUtil {

    private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##,##.##");

    private final String COMPLETED_COLOR = "§a";
    private final String NOT_COMPLETED_COLOR = "§8";

    public String getSimpleProgressBar(double current, double max) {
        return getSimpleProgressBar(current, max, '▍');
    }

    public String getSimpleProgressBar(double current, double max, char symbol) {
        return getProgressBar(current, max, 10, symbol, COMPLETED_COLOR, NOT_COMPLETED_COLOR);
    }

    public String getProgressBar(
            double current, double max, int totalBars, char symbol, String completedColor, String notCompletedColor) {
        double percent = current / max;
        if (percent >= 1.0D) {
            percent = 1.0D;
        }

        int progressBars = (int) ((double) totalBars * percent);
        String completeString = completedColor + Strings.repeat("" + symbol, progressBars);
        return percent == 1.0D
                ? completeString
                : completeString + notCompletedColor + Strings.repeat("" + symbol, totalBars - progressBars);
    }

    public String getProgress(double current, double max) {
        double expression = 100.0D * (current / max);
        return (expression < 100.0D ? DECIMAL_FORMAT.format(expression) : "100") + "%";
    }
}
