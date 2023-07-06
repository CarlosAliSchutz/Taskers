package br.com.cwi.api.util;

import br.com.cwi.crescer.api.domain.Dificuldade;
import br.com.cwi.crescer.api.util.CalculaXP;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CalculaXpTest {

    @Test
    @DisplayName("deveCalcularOXpDoHabitoFacil")
    void deveCalcularOXpDoHabitoFacil() {
        Integer XP_HABITO_FACIL = 5;
        Integer xpRecebido = CalculaXP.calcularXPdoHabito(Dificuldade.FACIL);
        assertEquals(XP_HABITO_FACIL, xpRecebido);
    }
    @Test
    @DisplayName("deveCalcularOXpDoHabitoMedio")
    void deveCalcularOXpDoHabitoMedio() {
        Integer XP_HABITO_MEDIO = 10;
        Integer xpRecebido = CalculaXP.calcularXPdoHabito(Dificuldade.MEDIO);
        assertEquals(XP_HABITO_MEDIO, xpRecebido);
    }
    @Test
    @DisplayName("deveCalcularOXpDoHabitoDificil")
    void deveCalcularOXpDoHabitoDificil() {
        Integer XP_HABITO_DIFICIL = 20;
        Integer xpRecebido = CalculaXP.calcularXPdoHabito(Dificuldade.DIFICIL);
        assertEquals(XP_HABITO_DIFICIL, xpRecebido);
    }

    @Test
    @DisplayName("deveCalcularOXpDoAfazerFacil")
    void deveCalcularOXpDoAfazerFacil() {
        Integer XP_AFAZER_FACIL = 20;
        Integer xpRecebido = CalculaXP.calcularXPdoAfazer(Dificuldade.FACIL);
        assertEquals(XP_AFAZER_FACIL, xpRecebido);
    }
    @Test
    @DisplayName("deveCalcularOXpDoAfazerMedio")
    void deveCalcularOXpDoAfazerMedio() {
        Integer XP_AFAZER_MEDIO = 50;
        Integer xpRecebido = CalculaXP.calcularXPdoAfazer(Dificuldade.MEDIO);
        assertEquals(XP_AFAZER_MEDIO, xpRecebido);
    }
    @Test
    @DisplayName("deveCalcularOXpDoAfazerDificil")
    void deveCalcularOXpDoAfazerDificil() {
        Integer XP_AFAZER_DIFICIL = 100;
        Integer xpRecebido = CalculaXP.calcularXPdoAfazer(Dificuldade.DIFICIL);
        assertEquals(XP_AFAZER_DIFICIL, xpRecebido);
    }

    @Test
    @DisplayName("deveCalcularOXpDaDiariaFacil")
    void deveCalcularOXpDaDiariaFacil() {
        Integer XP_DIARIA_FACIL = 25;
        Integer xpRecebido = CalculaXP.calcularXPdaDiaria(Dificuldade.FACIL);
        assertEquals(XP_DIARIA_FACIL, xpRecebido);
    }
    @Test
    @DisplayName("deveCalcularOXpDaDiariaMedio")
    void deveCalcularOXpDaDiariaMedio() {
        Integer XP_DIARIA_MEDIO = 35;
        Integer xpRecebido = CalculaXP.calcularXPdaDiaria(Dificuldade.MEDIO);
        assertEquals(XP_DIARIA_MEDIO, xpRecebido);
    }
    @Test
    @DisplayName("deveCalcularOXpDaDiariaDificil")
    void deveCalcularOXpDaDiariaDificil() {
        Integer XP_DIARIA_DIFICIL = 50;
        Integer xpRecebido = CalculaXP.calcularXPdaDiaria(Dificuldade.DIFICIL);
        assertEquals(XP_DIARIA_DIFICIL, xpRecebido);
    }
}
