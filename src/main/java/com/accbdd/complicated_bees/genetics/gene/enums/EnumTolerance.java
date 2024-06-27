package com.accbdd.complicated_bees.genetics.gene.enums;

import com.accbdd.complicated_bees.ComplicatedBees;
import net.minecraft.network.chat.Component;

public enum EnumTolerance {
    NONE("NONE", 0, 0),
    BOTH_5("BOTH_5", 5, 5),
    BOTH_4("BOTH_4", 4, 4),
    BOTH_3("BOTH_3", 3, 3),
    BOTH_2("BOTH_2", 2, 2),
    BOTH_1("BOTH_1", 1, 1),
    UP_5("UP_5", 5, 0),
    UP_4("UP_4", 4, 0),
    UP_3("UP_3", 3, 0),
    UP_2("UP_2", 2, 0),
    UP_1("UP_1", 1, 0),
    DOWN_5("DOWN_5", 0, 5),
    DOWN_4("DOWN_4", 0, 4),
    DOWN_3("DOWN_3", 0, 3),
    DOWN_2("DOWN_2", 0, 2),
    DOWN_1("DOWN_1", 0, 1);

    public static final EnumTolerance[] VALUES = values();

    public final String name;
    public final int up;
    public final int down;

    EnumTolerance(String name, int up, int down) {
        this.name = name;
        this.up = up;
        this.down = down;
    }

    public static EnumTolerance getFromString(String str) {
        return switch (str.toUpperCase()) {
            case "NONE" -> NONE;
            case "BOTH_5" -> BOTH_5;
            case "BOTH_4" -> BOTH_4;
            case "BOTH_3" -> BOTH_3;
            case "BOTH_2" -> BOTH_2;
            case "BOTH_1" -> BOTH_1;
            case "UP_5" -> UP_5;
            case "UP_4" -> UP_4;
            case "UP_3" -> UP_3;
            case "UP_2" -> UP_2;
            case "UP_1" -> UP_1;
            case "DOWN_5" -> DOWN_5;
            case "DOWN_4" -> DOWN_4;
            case "DOWN_3" -> DOWN_3;
            case "DOWN_2" -> DOWN_2;
            case "DOWN_1" -> DOWN_1;
            default -> {
                ComplicatedBees.LOGGER.warn("tried to convert unknown string {} to tolerance; returning NONE", str);
                yield NONE;
            }
        };
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Component getTranslationKey() {
        return Component.translatable("gui.complicated_bees.tolerance." + this.toString().toLowerCase());
    }
}
