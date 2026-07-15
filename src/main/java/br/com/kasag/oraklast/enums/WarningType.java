package br.com.kasag.oraklast.enums;

public enum WarningType {
    T2("dobro do volume padrão", 1),
    T3("triplo do volume padrão", 2),
    HMAX50P("50% do volume máximo histórico", 3),
    HMAX75P("75% do volume máximo histórico", 4);

    private final String desc;
    private final int ordinal;

    WarningType(String desc, int ordinal){
        this.desc = desc;
        this.ordinal = ordinal;
    }
}
