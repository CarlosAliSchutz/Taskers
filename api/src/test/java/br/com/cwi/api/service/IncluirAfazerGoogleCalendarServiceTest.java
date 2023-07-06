package br.com.cwi.api.service;

import br.com.cwi.api.factories.AfazerFactory;
import br.com.cwi.api.factories.CalendarGoogleApiFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.domain.Afazer;
import br.com.cwi.crescer.api.repository.AfazerRepository;
import br.com.cwi.crescer.api.repository.DiariaRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.AlterarAfazerGoogleCalendarService;
import br.com.cwi.crescer.api.service.GerarCalendarioGoogleApiService;
import br.com.cwi.crescer.api.service.IncluirAfazerGoogleCalendarService;
import com.google.api.services.calendar.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncluirAfazerGoogleCalendarServiceTest {

    @InjectMocks
    private IncluirAfazerGoogleCalendarService tested;

    @Mock
    private GerarCalendarioGoogleApiService gerarCalendarioGoogleApi;
    @Mock
    private AfazerRepository afazerRepository;

    @Test
    @DisplayName("Nao Deve Incluir Afazer No Google Calendar Quando Nao Possuir Conexao Com Api")
    void naoDeveIncluirAfazerNoGoogleCalendarQuandoNaoPossuirConexaoComApi() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Usuario usuario = UsuarioFactory.getUsuario();
        Calendar calendarioGoogle = CalendarGoogleApiFactory.getCalendar();

        when(gerarCalendarioGoogleApi.gerar()).thenReturn(calendarioGoogle);

        tested.incluir(usuario, afazer);

        verify(gerarCalendarioGoogleApi).gerar();
        verify(afazerRepository, never()).save(afazer);
    }

    @Test
    @DisplayName("Nao Deve Incluir Afazer No Google Calendar Quando Afazer Nao Possuir Integracao")
    void naoDeveIncluirAfazerNoGoogleCalendarQuandoAfazerNaoPossuirIntegracao() {
        Afazer afazer = AfazerFactory.getAfazerFacil();
        Usuario usuario = UsuarioFactory.getUsuarioComXP();

        tested.incluir(usuario, afazer);

        verify(gerarCalendarioGoogleApi, never()).gerar();
        verify(afazerRepository, never()).save(afazer);
    }

}
