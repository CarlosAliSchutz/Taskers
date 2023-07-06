package br.com.cwi.crescer.api.util;

import br.com.cwi.crescer.api.domain.Dificuldade;
import lombok.experimental.UtilityClass;

import static br.com.cwi.crescer.api.domain.Dificuldade.*;

@UtilityClass
public class CalculaXP {

    private static final Integer XP_HABITO_FACIL = 5;
    private static final Integer XP_HABITO_MEDIO = 10;
    private static final Integer XP_HABITO_DIFICIL = 20;

    private static final Integer XP_DIARIA_FACIL = 25;
    private static final Integer XP_DIARIA_MEDIO = 35;
    private static final Integer XP_DIARIA_DIFICIL = 50;

    private static final Integer XP_AFAZER_FACIL = 20;
    private static final Integer XP_AFAZER_MEDIO = 50;
    private static final Integer XP_AFAZER_DIFICIL = 100;


    public static Integer calcularXPdoHabito(Dificuldade dificuldade) {
        if(dificuldade.equals(MEDIO)) {
            return XP_HABITO_MEDIO;
        }
        if(dificuldade.equals(DIFICIL)) {
            return XP_HABITO_DIFICIL;
        }

        return XP_HABITO_FACIL;
    }

    public static Integer calcularXPdaDiaria(Dificuldade dificuldade) {
        if(dificuldade.equals(MEDIO)) {
            return XP_DIARIA_MEDIO;
        }
        if(dificuldade.equals(DIFICIL)) {
            return XP_DIARIA_DIFICIL;
        }

        return XP_DIARIA_FACIL;
    }

    public static Integer calcularXPdoAfazer(Dificuldade dificuldade) {
        if(dificuldade.equals(MEDIO)) {
            return XP_AFAZER_MEDIO;
        }
        if(dificuldade.equals(DIFICIL)) {
            return XP_AFAZER_DIFICIL;
        }

        return XP_AFAZER_FACIL;
    }

}
