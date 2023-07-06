package br.com.cwi.api.service;

import br.com.cwi.api.factories.CalendarGoogleApiFactory;
import br.com.cwi.api.factories.DiaFactory;
import br.com.cwi.api.factories.DiariaFactory;
import br.com.cwi.crescer.api.domain.Dia;
import br.com.cwi.crescer.api.domain.Diaria;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.service.AlterarDiariaGoogleCalendarService;
import br.com.cwi.crescer.api.service.DeletarDiariaGoogleCalendarService;
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
public class DeletarDiariaGoogleCalendarServiceTest {

    @InjectMocks
    private DeletarDiariaGoogleCalendarService tested;

    @Mock
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;
    @Mock
    private DiariaRepository diariaRepository;

    @Test
    @DisplayName("Deve Deletar Diaria No Google Calendar Quando Nao Possuir Conexao Com Api")
    void deveDeletarDiariaNoGoogleCalendarQuandoNaoPossuirConexaoComApi() {
        Diaria diaria = DiariaFactory.getDiariaFacil();
        diaria.setEventoGoogleCalendarId("12325");
        Calendar calendarioGoogle = CalendarGoogleApiFactory.getCalendar();

        when(gerarCalendarioGoogleApi.gerar()).thenReturn(calendarioGoogle);

        tested.deletar(diaria);

        verify(gerarCalendarioGoogleApi).gerar();
        verify(diariaRepository).save(diaria);
    }

    @Test
    @DisplayName("Nao Deve Deletar Diaria No Google Calendar Quando Afazer Nao Possuir Integracao")
    void naoDeveDeletarDiariaNoGoogleCalendarQuandoAfazerNaoPossuirIntegracao() {
        Diaria diaria = DiariaFactory.getDiariaFacil();

        tested.deletar(diaria);

        verify(gerarCalendarioGoogleApi, never()).gerar();
        verify(diariaRepository, never()).save(diaria);
    }

}
