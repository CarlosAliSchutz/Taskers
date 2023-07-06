package br.com.cwi.api.util;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.CalendarGoogleApiFactory;
import br.com.cwi.api.factories.DiaFactory;
import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.util.GeraDiariaParaCalendarioGoogle;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GeraDiariaParaCalendarioGoogleTest {

    @Test
    @DisplayName("Deve Gerar Uma Diaria Completa Para O Calendario Do Google")
    void deveGerarUmaDiariaComTodosOsDiasParaOCalendarioDoGoogle() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        List<Dia> dias = DiaFactory.getListaCompleta();
        diaria.setDias(dias);

        Event diariaParaCalendario = GeraDiariaParaCalendarioGoogle.gerar(diaria);

        assertEquals(diaria.getNome(), diariaParaCalendario.getSummary());
        assertEquals(diaria.getDescricao(), diariaParaCalendario.getDescription());
    }

    @Test
    @DisplayName("Deve Gerar Uma Diaria Com Apenas Alguns Dias Para O Calendario Do Google")
    void deveGerarUmaDiariaComApenasAlgunsDiasParaOCalendarioDoGoogle() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        List<Dia> dias = DiaFactory.getLista();
        diaria.setDias(dias);

        Event diariaParaCalendario = GeraDiariaParaCalendarioGoogle.gerar(diaria);

        assertEquals(diaria.getNome(), diariaParaCalendario.getSummary());
        assertEquals(diaria.getDescricao(), diariaParaCalendario.getDescription());
    }


    @Test
    @DisplayName("Deve Gerar Uma Diaria Para O Calendario Do Google")
    void naoDeveLancarErroQuandoListaDeDiasDaDiariaForVazia() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        List<Dia> dias = DiaFactory.getListaVazia();
        diaria.setDias(dias);

        Event diariaParaCalendario = GeraDiariaParaCalendarioGoogle.gerar(diaria);

        assertEquals(diaria.getNome(), diariaParaCalendario.getSummary());
        assertEquals(diaria.getDescricao(), diariaParaCalendario.getDescription());
    }
}
