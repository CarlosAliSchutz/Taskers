package br.com.cwi.api.service.core;

import br.com.cwi.crescer.api.service.core.NowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NowServiceTest {

    @InjectMocks
    private NowService tested;

    @Test
    @DisplayName("Deve Retornar A Hora Atual")
    void deveRetornarAHoraAtual() {
        tested.horaAtual();
    }

    @Test
    @DisplayName("Deve Retornar O Numero Do Dia Da Semana")
    void deveRetornarONumeroDoDiaDaSemana() {
        Long numeroDoDiaDaSemana = (long) LocalDate.now().getDayOfWeek().getValue();

        Long response = tested.numeroDiaDaSemana();

        assertEquals(numeroDoDiaDaSemana, response);
    }

}
