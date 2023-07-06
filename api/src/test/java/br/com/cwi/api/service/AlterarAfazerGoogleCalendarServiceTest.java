package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.CalendarGoogleApiFactory;
import br.com.cwi.api.factories.RequestFactory;
import br.com.cwi.crescer.api.controller.request.AfazerRequest;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.service.AlterarAfazerGoogleCalendarService;
import br.com.cwi.crescer.api.service.AlterarAfazerService;
import br.com.cwi.crescer.api.service.GerarCalendarioGoogleApiService;
import br.com.cwi.crescer.api.service.core.BuscarAfazerService;
import br.com.cwi.crescer.api.service.core.ValidarProprietarioService;
import com.google.api.services.calendar.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarAfazerGoogleCalendarServiceTest {

    @InjectMocks
    private AlterarAfazerGoogleCalendarService tested;

    @Mock
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;

    @Test
    @DisplayName("Deve Alterar Afazer No Google Calendar Quando Nao Possuir Conexao Com Api")
    void naoDeveAlterarAfazerNoGoogleCalendarQuandoNaoPossuirConexaoComApi() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        afazer.setEventoGoogleCalendarId("12325");
        Calendar calendarioGoogle = CalendarGoogleApiFactory.getCalendar();

        when(gerarCalendarioGoogleApi.gerar()).thenReturn(calendarioGoogle);

        tested.alterar(afazer);

        verify(gerarCalendarioGoogleApi).gerar();

    }

    @Test
    @DisplayName("Nao Deve Alterar Afazer No Google Calendar Quando Afazer Nao Possuir Integracao")
    void naoDeveAlterarAfazerNoGoogleCalendarQuandoAfazerNaoPossuirIntegracao() {
        Afazer afazer = AfazerFactory.getAfazerFacil();

        tested.alterar(afazer);

        verify(gerarCalendarioGoogleApi, never()).gerar();
    }

}
