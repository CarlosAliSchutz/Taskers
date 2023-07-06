package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.CalendarGoogleApiFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.service.AlterarAfazerGoogleCalendarService;
import br.com.cwi.crescer.api.service.DeletarAfazerGoogleCalendarService;
import br.com.cwi.crescer.api.service.GerarCalendarioGoogleApiService;
import com.google.api.services.calendar.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeletarAfazerGoogleCalendarServiceTest {

    @InjectMocks
    private DeletarAfazerGoogleCalendarService tested;

    @Mock
    private AfazerRepository afazerRepository;

    @Mock
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    @Test
    @DisplayName("Deve Deletar Afazer No Google Calendar Quando Nao Possuir Conexao Com Api")
    void deveDeletarAfazerNoGoogleCalendarQuandoNaoPossuirConexaoComApi() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        afazer.setEventoGoogleCalendarId("12325");
        Calendar calendarioGoogle = CalendarGoogleApiFactory.getCalendar();

        when(gerarCalendarioGoogleApi.gerar()).thenReturn(calendarioGoogle);

        tested.deletar(afazer);

        verify(gerarCalendarioGoogleApi).gerar();
        verify(afazerRepository).save(afazer);
    }

    @Test
    @DisplayName("Nao Deve Deletar Afazer No Google Calendar Quando Afazer Nao Possuir Integracao")
    void naoDeveDeletarAfazerNoGoogleCalendarQuandoAfazerNaoPossuirIntegracao() {
        Afazer afazer = AfazerFactory.getAfazerFacil();

        tested.deletar(afazer);

        verify(gerarCalendarioGoogleApi, never()).gerar();
        verify(afazerRepository, never()).save(afazer);
    }

}
