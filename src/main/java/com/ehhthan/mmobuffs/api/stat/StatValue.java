package com.ehhthan.mmobuffs.api.stat;

import org.jetbrains.annotations.NotNull;

public class StatValue {
    private final double value_base;
    private final double value_scale;

    private final ValueType type;

    public StatValue(@NotNull String value) {
        String base_string;
        String scale_string;

        if (value.contains("+")) {
            String[] split = value.split("\\+");
            base_string = split[0];
            scale_string = split[1];
        } else {
            base_string = value;
            scale_string = value;
        }

        if (base_string.endsWith("%")) {
            base_string = base_string.substring(0, base_string.length() - 1);
        }
        if (scale_string.endsWith("%")) {
            scale_string = scale_string.substring(0, scale_string.length() - 1);
            this.type = ValueType.RELATIVE;
        } else {
            this.type = ValueType.FLAT;
        }

        try {
            this.value_base = Double.parseDouble(base_string);
            this.value_scale = Double.parseDouble(scale_string);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Stat value '" + value + "' is not a valid number.");
        }
    }

    public StatValue(double value) {
        this(value, ValueType.FLAT);
    }

    public StatValue(double value, @NotNull ValueType type) {
        this.value_base = value;
        this.value_scale = value;
        this.type = type;
    }

    public double getValue_base() {
        return value_base;
    }

    public double getValue_scale() {
        return value_scale;
    }

    public @NotNull ValueType getType() {
        return type;
    }

    @Override
    public @NotNull String toString() {
        return String.valueOf(
            switch (type) {
                case FLAT -> value_base + "+" + value_scale;
                case RELATIVE -> value_base + "+" + value_scale + '%';
            }
        );
    }

    public enum ValueType {
        FLAT,
        RELATIVE
    }
}
