package com.ads.enade.enums;

public enum TipoQuestao {

    GERAL("GERAL"),
    ESPECIFICA("ESPECIFICA");

    private String typeEnum;

    TipoQuestao(String typeEnum) {
        this.typeEnum = typeEnum;
    }

    public static TipoQuestao from(String typeVerify) {

        for (TipoQuestao t : values()) {
            if (t.typeEnum.equalsIgnoreCase(typeVerify)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Tipo de questão não encontrado: " + typeVerify);
    }
}
