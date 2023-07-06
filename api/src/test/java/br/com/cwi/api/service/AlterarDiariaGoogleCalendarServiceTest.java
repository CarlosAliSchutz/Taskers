package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.CalendarGoogleApiFactory;
import br.com.cwi.api.factories.DiaFactory;
import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.service.AlterarAfazerGoogleCalendarService;
import br.com.cwi.crescer.api.service.AlterarDiariaGoogleCalendarService;
import br.com.cwi.crescer.api.service.GerarCalendarioGoogleApiService;
import com.google.api.services.calendar.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarDiariaGoogleCalendarServiceTest {

    @InjectMocks
    private AlterarDiariaGoogleCalendarService tested;

    @Mock
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    @Test
    @DisplayName("Deve Alterar Diaria No Google Calendar Quando Nao Possuir Conexao Com Api")
    void naoDeveAlterarDiariaNoGoogleCalendarQuandoNaoPossuirConexaoComApi() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        List<Dia> dias = DiaFactory.getLista();
        diaria.setDias(dias);
        diaria.setEventoGoogleCalendarId("12325");
        Calendar calendarioGoogle = CalendarGoogleApiFactory.getCalendar();

        when(gerarCalendarioGoogleApi.gerar()).thenReturn(calendarioGoogle);

        tested.alterar(diaria);

        verify(gerarCalendarioGoogleApi).gerar();

    }

    @Test
    @DisplayName("Nao Deve Alterar Diaria No Google Calendar Quando Afazer Nao Possuir Integracao")
    void naoDeveAlterarDiariaNoGoogleCalendarQuandoAfazerNaoPossuirIntegracao() {
        Diaria diaria = DiariaFactory.getDiariaFacil();

        tested.alterar(diaria);

        verify(gerarCalendarioGoogleApi, never()).gerar();
    }

}
