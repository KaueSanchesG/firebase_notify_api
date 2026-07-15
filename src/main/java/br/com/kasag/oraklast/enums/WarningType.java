package br.com.kasag.oraklast.enums;

import java.util.Arrays;

public enum WarningType {
    T2("dobro do volume padrão", 1),
    T3("triplo do volume padrão", 2),
    HMAX50P("50% do volume máximo histórico", 3),
    HMAX75P("75% do volume máximo histórico", 4);

    private final String desc;
    private final int index;

    WarningType(String desc, int ordinal) {
        this.desc = desc;
        this.index = ordinal;
    }

    public static String getDesc(int ordinal) {
        return Arrays.stream(WarningType.values())
                .filter(warningType -> warningType.index == ordinal)
                .map(warningType -> warningType.desc)
                .findAny()
                .orElse("");
    }
}
